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
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

public class PubSubSampleApplication {

  /**
   * Driver for the Pub/Sub Sample application which publishes a message to a specified topic.
   *
   * <p>The topic should be specified over command line in the form:
   * java -jar YOUR_JAR.jar projects/YOUR_PROJECT_ID/topics/YOUR_TOPIC_NAME
   */
  public static void main(String[] args) throws Exception {
    if (args.length <= 0) {
      System.err.println("Error: Please provided a Pub/Sub topic name as an additional argument "
          + "to invoking this JAR. The command should be in the form: "
          + "java -jar YOUR_JAR.jar projects/YOUR_PROJECT_ID/topics/YOUR_TOPIC_NAME");
    }

    publishMessage(args[0]);
  }

  private static void publishMessage(String topicName) throws Exception {
    Publisher publisher =
        Publisher
            .newBuilder(topicName)
            .build();

    String message = "This is the published message.";
    ByteString data = ByteString.copyFromUtf8(message);
    PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

    publisher.publish(pubsubMessage);

    ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
    String messageId = messageIdFuture.get();

    System.out.println("Published message ID: " + messageId);
    System.out.println("Goodbye.");
  }
}
