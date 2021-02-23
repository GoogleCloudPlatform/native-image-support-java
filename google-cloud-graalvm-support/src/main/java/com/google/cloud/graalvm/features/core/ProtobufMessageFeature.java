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

package com.google.cloud.graalvm.features.core;

import static com.google.cloud.graalvm.features.NativeImageUtils.getMethodOrFail;
import static com.google.cloud.graalvm.features.NativeImageUtils.registerClassHierarchyForReflection;

import com.oracle.svm.core.annotate.AutomaticFeature;
import java.lang.reflect.Method;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeReflection;

/**
 * A feature which registers reflective usages of the GRPC Protobuf libraries.
 */
@AutomaticFeature
public class ProtobufMessageFeature implements Feature {

  private static final String PROTO_MESSAGE_CLASS = "com.google.protobuf.GeneratedMessageV3";
  private static final String PROTO_ENUM_CLASS = "com.google.protobuf.ProtocolMessageEnum";
  private static final String ENUM_VAL_DESCRIPTOR_CLASS =
      "com.google.protobuf.Descriptors$EnumValueDescriptor";

  @Override
  public void beforeAnalysis(BeforeAnalysisAccess access) {
    Class<?> protoMessageClass = access.findClassByName(PROTO_MESSAGE_CLASS);
    if (PROTO_MESSAGE_CLASS != null) {
      registerClassHierarchyForReflection(
          access, "com.google.protobuf.DescriptorProtos");

      Method internalAccessorMethod =
          getMethodOrFail(protoMessageClass, "internalGetFieldAccessorTable");

      // Finds every class whose `internalGetFieldAccessorTable()` and registers the class.
      // `internalGetFieldAccessorTable()` is used downstream to access the class reflectively.
      access.registerMethodOverrideReachabilityHandler(
          (duringAccess, method) ->
            registerClassHierarchyForReflection(
                duringAccess, method.getDeclaringClass().getName()),
          internalAccessorMethod);
    }

    Class<?> protoEnumClass = access.findClassByName(PROTO_ENUM_CLASS);
    if (PROTO_ENUM_CLASS != null) {
      // Finds every reachable proto enum class and registers specific methods for reflection.
      access.registerSubtypeReachabilityHandler(
          (duringAccess, subtypeClass) -> {
            if (!PROTO_ENUM_CLASS.equals(subtypeClass.getName())) {
              Method method = getMethodOrFail(
                  subtypeClass,
                  "valueOf",
                  duringAccess.findClassByName(ENUM_VAL_DESCRIPTOR_CLASS));
              RuntimeReflection.register(method);

              method = getMethodOrFail(subtypeClass, "getValueDescriptor");
              RuntimeReflection.register(method);
            }
          },
          protoEnumClass);
    }
  }
}
