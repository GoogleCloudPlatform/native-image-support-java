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

import static com.google.cloud.graalvm.features.NativeImageUtils.registerPackageForReflection;

import com.oracle.svm.core.annotate.AutomaticFeature;
import org.graalvm.nativeimage.hosted.Feature;

/**
 * Configures Native Image settings for the Google Cloud Storage client libraries.
 */
@AutomaticFeature
public class CloudTasksFeature implements Feature {

  private static final String CLOUD_TASKS_CLASS = "com.google.cloud.tasks.v2.Task";

  @Override
  public void beforeAnalysis(BeforeAnalysisAccess access) {
    Class<?> cloudTasksClass = access.findClassByName(CLOUD_TASKS_CLASS);
    if (cloudTasksClass != null) {
      // Without this, the toString() model methods don't work
      registerPackageForReflection(
            access, "com.google.cloud.tasks.v2"
      );
    }
  }
}
