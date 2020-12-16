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

import static com.google.cloud.graalvm.features.NativeImageUtils.registerClassForReflection;
import static com.google.cloud.graalvm.features.NativeImageUtils.registerClassHierarchyForReflection;
import static com.google.cloud.graalvm.features.NativeImageUtils.registerPackageForReflection;

import com.oracle.svm.core.annotate.AutomaticFeature;
import org.graalvm.nativeimage.hosted.Feature;

@AutomaticFeature
public class BigQueryFeature implements Feature {

  private static final String BIG_QUERY_CLASS = "com.google.api.services.bigquery.Bigquery";

  @Override
  public void beforeAnalysis(BeforeAnalysisAccess access) {
    Class<?> bigQueryClass = access.findClassByName(BIG_QUERY_CLASS);

    if (bigQueryClass != null) {
      registerClassHierarchyForReflection(
          access, "com.google.api.services.bigquery.Bigquery");

      // Register all the JSON object classes
      registerPackageForReflection(
          access, "com.google.api.services.bigquery.model");

      registerClassForReflection(
          access, "com.google.api.services.bigquery.BigqueryRequest");
    }
  }
}
