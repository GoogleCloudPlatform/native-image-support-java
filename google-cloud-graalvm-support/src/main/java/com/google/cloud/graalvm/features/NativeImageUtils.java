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

import java.lang.reflect.Modifier;
import java.util.logging.Logger;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.hosted.Feature.FeatureAccess;
import org.graalvm.nativeimage.hosted.RuntimeReflection;
import org.graalvm.nativeimage.impl.RuntimeReflectionSupport;

public class NativeImageUtils {

  private static final Logger LOGGER = Logger.getLogger(NativeImageUtils.class.getName());

  /**
   * Registers a class for reflective construction via its default constructor.
   */
  public static void registerForReflectiveInstantiation(FeatureAccess access, String className) {
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
  public static void registerClassForReflection(FeatureAccess access, String name) {
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

  /**
   * Registers the transitive class hierarchy of the provided {@code className} for reflection.
   *
   * <p>The transitive class hierarchy contains the class itself and its transitive set of
   * *non-private* nested subclasses.
   */
  public static void registerClassHierarchyForReflection(FeatureAccess access, String className) {
    Class<?> clazz = access.findClassByName(className);
    if (clazz != null) {
      registerClassForReflection(access, className);
      for (Class<?> nestedClass : clazz.getDeclaredClasses()) {
        if (!Modifier.isPrivate(nestedClass.getModifiers())) {
          registerClassHierarchyForReflection(access, nestedClass.getName());
        }
      }
    } else {
      LOGGER.warning(
          "Failed to find " + className + " on the classpath for reflection.");
    }
  }

  /**
   * Registers a class for unsafe reflective field access.
   */
  public static void registerForUnsafeFieldAccess(
      FeatureAccess access, String className, String... fields) {
    Class<?> clazz = access.findClassByName(className);
    if (clazz != null) {
      RuntimeReflectionSupport reflectionSupport =
          ImageSingletons.lookup(RuntimeReflectionSupport.class);
      RuntimeReflection.register(clazz);
      for (String fieldName : fields) {
        try {
          reflectionSupport.register(false, true, clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException ex) {
          LOGGER.warning("Failed to register field " + fieldName + " for class " + className);
          LOGGER.warning(ex.getMessage());
        }
      }
    } else {
      LOGGER.warning(
          "Failed to find " + className
              + " on the classpath for unsafe fields access registration.");
    }
  }
}
