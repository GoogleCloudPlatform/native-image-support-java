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

      registerClassForReflection(
          access, "com.google.api.services.storage.model.BucketAccessControl");
      registerClassForReflection(
          access, "com.google.api.services.storage.model.BucketAccessControl$ProjectTeam");
      registerClassForReflection(
          access, "com.google.api.services.storage.model.ObjectAccessControl");
      registerClassForReflection(
          access, "com.google.api.services.storage.model.ObjectAccessControl$ProjectTeam");
      registerClassForReflection(
          access, "com.google.api.services.storage.model.StorageObject");
      registerClassForReflection(
          access, "com.google.api.services.storage.model.StorageObject$Owner");

      registerClassForReflection(
          access, "com.google.api.services.storage.StorageRequest");
      registerClassForReflection(
          access, "com.google.api.services.storage.model.Bucket");
      registerClassForReflection(
          access, "com.google.api.services.storage.model.Bucket$Owner");
      registerClassForReflection(
          access, "com.google.api.services.storage.model.Bucket$IamConfiguration");
      registerClassForReflection(
          access, "com.google.api.services.storage.model.Bucket"
              + "$IamConfiguration$BucketPolicyOnly");
      registerClassForReflection(
          access, "com.google.api.services.storage.model.Bucket"
              + "$IamConfiguration$UniformBucketLevelAccess");
      registerClassForReflection(
          access, "com.google.api.services.storage.Storage$Buckets$Insert");
      registerClassForReflection(
          access, "com.google.api.services.storage.Storage$Buckets$Get");
      registerClassForReflection(
          access, "com.google.api.services.storage.Storage$Buckets$Delete");
      registerClassForReflection(
          access, "com.google.api.services.storage.Storage$Objects$Insert");
      registerClassForReflection(
          access, "com.google.api.services.storage.Storage$Objects$Get");
      registerClassForReflection(
          access, "com.google.api.services.storage.Storage$Objects$Delete");
    }
  }
}
