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
import static com.google.cloud.graalvm.features.NativeImageUtils.registerForReflectiveInstantiation;
import static com.google.cloud.graalvm.features.NativeImageUtils.registerForUnsafeFieldAccess;

import com.oracle.svm.core.annotate.AutomaticFeature;
import org.graalvm.nativeimage.hosted.Feature;

/**
 * Configures Native Image settings for the grpc-netty-shaded dependency.
 */
@AutomaticFeature
public class GrpcNettyFeature implements Feature {

  private static final String GRPC_NETTY_SHADED_CLASS =
      "io.grpc.netty.shaded.io.grpc.netty.NettyServer";

  @Override
  public void beforeAnalysis(BeforeAnalysisAccess access) {
    Class<?> nettyShadedClass = access.findClassByName(GRPC_NETTY_SHADED_CLASS);
    if (nettyShadedClass != null) {
      registerClassHierarchyForReflection(
          access, "com.google.protobuf.DescriptorProtos");

      registerClassForReflection(
          access, "io.grpc.netty.shaded.io.netty.util.internal.NativeLibraryUtil");

      registerForReflectiveInstantiation(
          access, "io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel");

      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded."
              + "org.jctools.queues.MpscArrayQueueProducerIndexField",
          "producerIndex");
      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded."
              + "org.jctools.queues.MpscArrayQueueProducerLimitField",
          "producerLimit");
      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded."
              + "org.jctools.queues.MpscArrayQueueConsumerIndexField",
          "consumerIndex");
      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded."
              + "org.jctools.queues.BaseMpscLinkedArrayQueueProducerFields",
          "producerIndex");
      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded."
              + "org.jctools.queues.BaseMpscLinkedArrayQueueColdProducerFields",
          "producerLimit");
      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded."
              + "org.jctools.queues.BaseMpscLinkedArrayQueueConsumerFields",
          "consumerIndex");
    }
  }

}
