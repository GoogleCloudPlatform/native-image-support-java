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

package com.example.function;

import com.google.cloud.ServiceOptions;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import java.io.IOException;
import java.time.Instant;

public class PubSubHttpFunction implements HttpFunction {

  private final Publisher publisher;

  /**
   * Constructs the function.
   */
  public PubSubHttpFunction() throws IOException {
    this.publisher =
        Publisher.newBuilder(
            TopicName.of(
                ServiceOptions.getDefaultProjectId(),
                "test-topic"))
            .build();
  }

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
    String message = "Test message from Cloud Function. Time: " + Instant.now();
    ByteString data = ByteString.copyFromUtf8(message);
    PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
    publisher.publish(pubsubMessage);

    httpResponse.getWriter().write("Message published to: test-topic");
  }
}
