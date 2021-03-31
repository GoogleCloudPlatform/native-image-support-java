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

/**
 * Registers Spanner library classes for reflection.
 */
@AutomaticFeature
public class SpannerFeature implements Feature {

  private static final String SPANNER_CLASS = "com.google.spanner.v1.SpannerGrpc";

  @Override
  public void beforeAnalysis(BeforeAnalysisAccess access) {
    Class<?> spannerClass = access.findClassByName(SPANNER_CLASS);
    if (spannerClass != null) {
      registerClassHierarchyForReflection(
          access, "com.google.spanner.admin.database.v1.Database");
      registerClassHierarchyForReflection(
          access, "com.google.spanner.admin.instance.v1.Instance");
      registerClassForReflection(
          access, "com.google.spanner.admin.database.v1.RestoreInfo");
    }
  }
}
