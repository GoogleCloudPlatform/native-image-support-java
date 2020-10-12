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

package com.google.cloud.graalvm.features;

import static com.google.cloud.graalvm.features.NativeImageUtils.registerForReflection;
import static com.google.cloud.graalvm.features.NativeImageUtils.registerForReflectiveInstantiation;

import com.oracle.svm.core.annotate.AutomaticFeature;
import com.oracle.svm.core.configure.ResourcesRegistry;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.hosted.Feature;

/**
 * Configures Native Image settings for the Google JSON Client.
 */
@AutomaticFeature
public class GoogleJsonClientFeature implements Feature {

  private static final String GOOGLE_API_CLIENT_CLASS =
      "com.google.api.client.googleapis.GoogleUtils";

  private static final String GOOGLE_HTTP_CLIENT_CLASS =
      "com.google.api.client.json.GenericJson";

  @Override
  public void beforeAnalysis(BeforeAnalysisAccess access) {
    loadApiClient(access);
    loadHttpClient(access);
  }

  private void loadApiClient(BeforeAnalysisAccess access) {
    // For com.google.api-client:google-api-client
    Class<?> googleApiClass = access.findClassByName(GOOGLE_API_CLIENT_CLASS);

    if (googleApiClass != null) {
      // Resources
      ResourcesRegistry resourcesRegistry = ImageSingletons.lookup(ResourcesRegistry.class);
      resourcesRegistry.addResources(
          "\\Qcom/google/api/client/googleapis/google-api-client.properties\\E");

      // Reflection calls
      registerForReflectiveInstantiation(
          access, "com.google.api.client.googleapis.json.GoogleJsonError");
      registerForReflectiveInstantiation(
          access, "com.google.api.client.googleapis.json.GoogleJsonError$ErrorInfo");
      registerForReflection(
          access, "com.google.api.client.googleapis.services.AbstractGoogleClientRequest");
      registerForReflection(
          access, "com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest");
    }
  }

  private void loadHttpClient(BeforeAnalysisAccess access) {
    // For com.google.http-client:google-http-client
    Class<?> googleHttpClientClass = access.findClassByName(GOOGLE_HTTP_CLIENT_CLASS);

    if (googleHttpClientClass != null) {
      registerForReflection(
          access, "com.google.api.client.util.GenericData");
      registerForReflection(
          access, "com.google.api.client.json.GenericJson");
      registerForReflection(
          access, "com.google.api.client.json.webtoken.JsonWebToken");
      registerForReflection(
          access, "com.google.api.client.json.webtoken.JsonWebToken$Header");
      registerForReflection(
          access, "com.google.api.client.json.webtoken.JsonWebToken$Payload");
      registerForReflection(
          access,  "com.google.api.client.json.webtoken.JsonWebSignature$Header");
      registerForReflection(
          access, "com.google.api.client.json.webtoken.JsonWebSignature");
      registerForReflection(
          access, "com.google.api.client.http.UrlEncodedContent");
      registerForReflection(
          access, "com.google.api.client.http.GenericUrl");
      registerForReflection(
          access, "com.google.api.client.http.HttpRequest");
      registerForReflection(
          access, "com.google.api.client.http.HttpHeaders");
    }
  }
}
