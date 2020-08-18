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

public class Driver {

  /**
   * Driver for the Sample application compiled with GraalVM which uses Pub/Sub client libraries.
   */
  public static void main(String[] args) throws Exception {
    Publisher publisher =
        Publisher
            .newBuilder("projects/my-kubernetes-codelab-217414/topics/exampleTopic")
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
