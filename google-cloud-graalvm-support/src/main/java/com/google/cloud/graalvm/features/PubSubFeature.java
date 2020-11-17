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

import com.oracle.svm.core.annotate.AutomaticFeature;
import org.graalvm.nativeimage.hosted.Feature;

@AutomaticFeature
public class PubSubFeature implements Feature {

  private static final String PUBSUB_CLASS = "com.google.cloud.pubsub.v1.Publisher";

  @Override
  public void beforeAnalysis(BeforeAnalysisAccess access) {
    Class<?> pubsubClass = access.findClassByName(PUBSUB_CLASS);
    if (pubsubClass != null) {
      registerClassHierarchyForReflection(access, "com.google.iam.v1.Policy");
      registerClassForReflection(access, "com.google.iam.v1.Binding");
      registerClassHierarchyForReflection(
          access, "com.google.iam.v1.TestIamPermissionsResponse");
    }
  }
}
