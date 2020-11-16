/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.google.api.core.ApiFuture;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.stub.GrpcSubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStubSettings;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.AcknowledgeRequest;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.PullRequest;
import com.google.pubsub.v1.PullResponse;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.ReceivedMessage;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple utility class demonstrating common operations in Google Cloud Pub/Sub.
 */
public class PubSubUtils {
  private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();

  static Topic createTopic(String topicId) throws IOException {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topicName = TopicName.of(PROJECT_ID, topicId);
      Topic topic = topicAdminClient.createTopic(topicName);
      return topic;
    }
  }

  static Subscription createSubscription(String subscriptionId, String topicId) throws IOException {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      TopicName topicName = TopicName.of(PROJECT_ID, topicId);
      ProjectSubscriptionName subscriptionName =
          ProjectSubscriptionName.of(PROJECT_ID, subscriptionId);
      Subscription subscription =
          subscriptionAdminClient.createSubscription(
              subscriptionName, topicName, PushConfig.getDefaultInstance(), 10);
      return subscription;
    }
  }

  static void publishMessage(String topicId, String payload) throws Exception {
    Publisher publisher =
        Publisher
            .newBuilder(TopicName.of(PROJECT_ID, topicId))
            .build();

    ByteString data = ByteString.copyFromUtf8(payload);
    PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

    publisher.publish(pubsubMessage);

    ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
    String messageId = messageIdFuture.get();

    System.out.println("Published message with ID: " + messageId);
  }

  static String subscribeSync(String subscriptionId) throws IOException {
    SubscriberStubSettings subscriberStubSettings =
        SubscriberStubSettings.newBuilder()
            .setTransportChannelProvider(
                SubscriberStubSettings.defaultGrpcTransportProviderBuilder()
                    .setMaxInboundMessageSize(4 * 1024 * 1024) // 4MB message size.
                    .build())
            .build();

    String result = "NO MESSAGES IN TOPIC.";
    try (SubscriberStub subscriber = GrpcSubscriberStub.create(subscriberStubSettings)) {
      String subscriptionName = ProjectSubscriptionName.format(PROJECT_ID, subscriptionId);
      PullRequest pullRequest =
          PullRequest.newBuilder()
              .setMaxMessages(1)
              .setSubscription(subscriptionName)
              .build();

      PullResponse pullResponse = subscriber.pullCallable().call(pullRequest);
      List<String> ackIds = new ArrayList<>();
      for (ReceivedMessage message : pullResponse.getReceivedMessagesList()) {
        String payload = message.getMessage().getData().toStringUtf8();
        ackIds.add(message.getAckId());
        System.out.println("Received Payload: " + payload);
        result = payload;
      }

      if (!ackIds.isEmpty()) {
        AcknowledgeRequest acknowledgeRequest =
            AcknowledgeRequest.newBuilder()
                .setSubscription(subscriptionName)
                .addAllAckIds(ackIds)
                .build();

        subscriber.acknowledgeCallable().call(acknowledgeRequest);
      }
    }

    return result;
  }

  static TopicName deleteTopic(String topicId) throws IOException {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topicName = TopicName.of(PROJECT_ID, topicId);
      topicAdminClient.deleteTopic(topicName);
      return topicName;
    }
  }

  static ProjectSubscriptionName deleteSubscription(String subscriptionId)
      throws IOException {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ProjectSubscriptionName subscriptionName =
          ProjectSubscriptionName.of(PROJECT_ID, subscriptionId);
      subscriptionAdminClient.deleteSubscription(subscriptionName);
      return subscriptionName;
    }
  }
}
