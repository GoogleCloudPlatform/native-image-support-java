/*
 * Copyright 2020-2021 Google LLC
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

import com.google.api.gax.rpc.HeaderProvider;
import com.google.cloud.pubsub.v1.stub.PublisherStubSettings;
import java.io.IOException;
import java.lang.reflect.Field;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * This is a sample application used to test that client headers are set correctly.
 */
@Path("/")
public class ExampleResource {

  /**
   * Returns the GRPC headers provided by the Pub/Sub API client.
   */
  @GET
  @Path("/headersGrpc")
  public String getHeadersGrpc() throws IOException {
    StubSettingsDelegate settingsDelegate =
        new StubSettingsDelegate(PublisherStubSettings.newBuilder());
    return settingsDelegate.getInternalHeaders().getHeaders().get("x-goog-api-client");
  }

  /**
   * Returns the JSON headers used by Storage, BigQuery, BigTable clients.
   */
  @GET
  @Path("/headersJson")
  public String getHeadersJson() throws Exception {
    // Access the JSON
    Class<?> apiVersionClass =
        Class.forName(
            "com.google.api.client.googleapis.services.AbstractGoogleClientRequest$ApiClientVersion");

    // Read the value of the headers in the static field.
    Field headerField = apiVersionClass.getDeclaredField("DEFAULT_VERSION");
    headerField.setAccessible(true);
    return (String) headerField.get(null);
  }

  /**
   * A delegate class to {@link PublisherStubSettings} which allows us to access
   * protected {@link PublisherStubSettings#getInternalHeaderProvider()}.
   */
  private static class StubSettingsDelegate extends PublisherStubSettings {
    protected StubSettingsDelegate(Builder settingsBuilder) throws IOException {
      super(settingsBuilder);
    }

    public HeaderProvider getInternalHeaders() {
      return this.getInternalHeaderProvider();
    }
  }
}
