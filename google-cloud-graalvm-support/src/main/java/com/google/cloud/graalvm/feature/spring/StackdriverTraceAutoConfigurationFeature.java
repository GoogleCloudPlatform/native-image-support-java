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

package com.google.cloud.graalvm.feature.spring;

import com.oracle.svm.core.annotate.AutomaticFeature;
import com.oracle.svm.core.jdk.proxy.DynamicProxyRegistry;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeReflection;

/**
 * Native Image feature configuration for the Stackdriver Trace Autoconfiguration class in
 * Spring Cloud GCP.
 */
@AutomaticFeature
final class StackdriverTraceAutoConfigurationFeature implements Feature {

  private static final String TRACE_AUTOCONFIGURATION_CLASS =
      "org.springframework.cloud.gcp.autoconfigure.trace.StackdriverTraceAutoConfiguration";

  @Override
  public void beforeAnalysis(BeforeAnalysisAccess access) {
    Class<?> traceAutoconfiguration = access.findClassByName(TRACE_AUTOCONFIGURATION_CLASS);
    if (traceAutoconfiguration != null) {

      // Register Reflection calls
      try {
        Class<?> managedChannel = access.findClassByName("io.grpc.ManagedChannel");
        RuntimeReflection.register(managedChannel.getMethod("shutdownNow"));
      } catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void duringSetup(DuringSetupAccess access) {
    Class<?> traceAutoconfiguration = access.findClassByName(TRACE_AUTOCONFIGURATION_CLASS);
    if (traceAutoconfiguration != null) {
      // Register Proxies
      addProxy("org.springframework.aop.SpringProxy", access);
      addProxy("org.springframework.aop.framework.Advised", access);
      addProxy("org.springframework.core.DecoratingProxy", access);
    }
  }

  private static void addProxy(String className, DuringSetupAccess access) {
    Class<?> proxy = access.findClassByName(className);
    DynamicProxyRegistry dynamicProxyRegistry = ImageSingletons.lookup(DynamicProxyRegistry.class);
    dynamicProxyRegistry.addProxyClass(proxy);
  }
}
