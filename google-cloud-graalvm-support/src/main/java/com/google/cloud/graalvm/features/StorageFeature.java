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
import org.graalvm.nativeimage.hosted.Feature;

/**
 * Configures Native Image settings for the Google Cloud Storage client libraries.
 */
@AutomaticFeature
public class StorageFeature implements Feature {

  private static final String STORAGE_CLASS = "com.google.api.services.storage.Storage";

  @Override
  public void beforeAnalysis(BeforeAnalysisAccess access) {
    Class<?> storageClass = access.findClassByName(STORAGE_CLASS);
    if (storageClass != null) {
      registerForReflectiveInstantiation(
          access, "com.google.api.services.storage.model.Bucket$Cors");
      registerForReflectiveInstantiation(
          access, "com.google.api.services.storage.model.Bucket$Lifecycle$Rule");
      registerForReflectiveInstantiation(
          access, "com.google.api.services.storage.model.Bucket$IamConfiguration$BucketPolicyOnly");
      registerForReflectiveInstantiation(
          access, "com.google.api.services.storage.model.Bucket"
              + "$IamConfiguration$UniformBucketLevelAccess");

      registerForReflection(
          access, "com.google.api.services.storage.model.BucketAccessControl");
      registerForReflection(
          access, "com.google.api.services.storage.model.BucketAccessControl$ProjectTeam");
      registerForReflection(
          access, "com.google.api.services.storage.model.ObjectAccessControl");
      registerForReflection(
          access, "com.google.api.services.storage.model.ObjectAccessControl$ProjectTeam");
      registerForReflection(
          access, "com.google.api.services.storage.model.StorageObject");
      registerForReflection(
          access, "com.google.api.services.storage.model.StorageObject$Owner");

      registerForReflection(
          access, "com.google.api.services.storage.StorageRequest");
      registerForReflection(
          access, "com.google.api.services.storage.model.Bucket");
      registerForReflection(
          access, "com.google.api.services.storage.model.Bucket$Owner");
      registerForReflection(
          access, "com.google.api.services.storage.model.Bucket$IamConfiguration");
      registerForReflection(
          access, "com.google.api.services.storage.model.Bucket"
              + "$IamConfiguration$BucketPolicyOnly");
      registerForReflection(
          access, "com.google.api.services.storage.model.Bucket"
              + "$IamConfiguration$UniformBucketLevelAccess");
      registerForReflection(
          access, "com.google.api.services.storage.Storage$Buckets$Insert");
      registerForReflection(
          access, "com.google.api.services.storage.Storage$Buckets$Get");
      registerForReflection(
          access, "com.google.api.services.storage.Storage$Buckets$Delete");
      registerForReflection(
          access, "com.google.api.services.storage.Storage$Objects$Insert");
      registerForReflection(
          access, "com.google.api.services.storage.Storage$Objects$Get");
      registerForReflection(
          access, "com.google.api.services.storage.Storage$Objects$Delete");
    }
  }
}
