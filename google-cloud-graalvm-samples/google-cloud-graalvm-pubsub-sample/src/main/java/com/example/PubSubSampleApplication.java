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
import com.google.api.gax.rpc.NotFoundException;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PubSubSampleApplication {

  private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();

  /**
   * Driver for the Pub/Sub Sample application which publishes a message to a specified topic.
   */
  public static void main(String[] args) throws Exception {
    String topicId = "graal-pubsub-test-" + UUID.randomUUID().toString();
    String subscriptionId = "graal-pubsub-test-sub" + UUID.randomUUID().toString();

    try {
      createTopic(topicId);
      createSubscription(subscriptionId, topicId);

      publishMessage(topicId);
      subscribeSync(subscriptionId);
    } finally {
      deleteSubscription(subscriptionId);
      deleteTopic(topicId);
    }
  }

  private static void createTopic(String topicId) throws IOException {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topicName = TopicName.of(PROJECT_ID, topicId);
      Topic topic = topicAdminClient.createTopic(topicName);
      System.out.println("Created topic: " + topic.getName() + " under project: " + PROJECT_ID);
    }
  }

  private static void createSubscription(String subscriptionId, String topicId) throws IOException {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      TopicName topicName = TopicName.of(PROJECT_ID, topicId);
      ProjectSubscriptionName subscriptionName =
          ProjectSubscriptionName.of(PROJECT_ID, subscriptionId);
      Subscription subscription =
          subscriptionAdminClient.createSubscription(
              subscriptionName, topicName, PushConfig.getDefaultInstance(), 10);
      System.out.println("Created pull subscription: " + subscription.getName());
    }
  }

  private static void publishMessage(String topicId) throws Exception {
    Publisher publisher =
        Publisher
            .newBuilder(TopicName.of(PROJECT_ID, topicId))
            .build();

    String message = "Pub/Sub Graal Test published message at timestamp: " + Instant.now();
    ByteString data = ByteString.copyFromUtf8(message);
    PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

    publisher.publish(pubsubMessage);

    ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
    String messageId = messageIdFuture.get();

    System.out.println("Published message with ID: " + messageId);
  }

  private static void subscribeSync(String subscriptionId) throws IOException {
    SubscriberStubSettings subscriberStubSettings =
        SubscriberStubSettings.newBuilder()
            .setTransportChannelProvider(
                SubscriberStubSettings.defaultGrpcTransportProviderBuilder()
                    .setMaxInboundMessageSize(20 * 1024 * 1024) // 20MB (maximum message size).
                    .build())
            .build();

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
      }

      AcknowledgeRequest acknowledgeRequest =
          AcknowledgeRequest.newBuilder()
              .setSubscription(subscriptionName)
              .addAllAckIds(ackIds)
              .build();

      subscriber.acknowledgeCallable().call(acknowledgeRequest);
    }
  }

  private static void deleteTopic(String topicId) throws IOException {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topicName = TopicName.of(PROJECT_ID, topicId);
      try {
        topicAdminClient.deleteTopic(topicName);
        System.out.println("Deleted topic " + topicName);
      } catch (NotFoundException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static void deleteSubscription(String subscriptionId)
      throws IOException {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ProjectSubscriptionName subscriptionName =
          ProjectSubscriptionName.of(PROJECT_ID, subscriptionId);
      try {
        subscriptionAdminClient.deleteSubscription(subscriptionName);
        System.out.println("Deleted subscription " + subscriptionName);
      } catch (NotFoundException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
