package com.google.cloud.graalvm.features;

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

      registerForReflectiveInstantiation(
          access, "io.grpc.netty.shaded.io.netty.channel.socket.nio.NioSocketChannel");

      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueProducerIndexField",
          "producerIndex");
      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueProducerLimitField",
          "producerLimit");
      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueConsumerIndexField",
          "consumerIndex");
      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueProducerFields",
          "producerIndex");
      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueColdProducerFields",
          "producerLimit");
      registerForUnsafeFieldAccess(
          access,
          "io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueConsumerFields",
          "consumerIndex");
    }
  }

}
