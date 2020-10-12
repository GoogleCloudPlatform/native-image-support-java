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

import java.util.logging.Logger;
import org.graalvm.nativeimage.hosted.Feature.FeatureAccess;
import org.graalvm.nativeimage.hosted.RuntimeReflection;

public class NativeImageUtils {

  private static final Logger LOGGER = Logger.getLogger(NativeImageUtils.class.getName());

  /**
   * Registers a class for reflective construction via its default constructor.
   */
  static void registerForReflectiveInstantiation(FeatureAccess access, String className) {
    Class<?> clazz = access.findClassByName(className);
    if (clazz != null) {
      RuntimeReflection.register(clazz);
      RuntimeReflection.registerForReflectiveInstantiation(clazz);
    } else {
      LOGGER.warning(
          "Failed to find " + className + " on the classpath for reflective instantiation.");
    }
  }

  /**
   * Registers an entire class for reflection use.
   */
  static void registerForReflection(FeatureAccess access, String name) {
    Class<?> clazz = access.findClassByName(name);
    if (clazz != null) {
      RuntimeReflection.register(clazz);
      RuntimeReflection.register(clazz.getDeclaredConstructors());
      RuntimeReflection.register(clazz.getDeclaredFields());
      RuntimeReflection.register(clazz.getDeclaredMethods());
    } else {
      LOGGER.warning(
          "Failed to find " + name + " on the classpath for reflection.");
    }
  }
}
