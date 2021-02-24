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

package com.google.cloud.graalvm.features.substitutions;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.RecomputeFieldValue;
import com.oracle.svm.core.annotate.RecomputeFieldValue.CustomFieldValueTransformer;
import com.oracle.svm.core.annotate.RecomputeFieldValue.Kind;
import com.oracle.svm.core.annotate.TargetClass;

/**
 * Substitution for setting Java version correctly in the Google Java Http Client.
 */
@TargetClass(className =
    "com.google.api.client.googleapis.services.AbstractGoogleClientRequest$ApiClientVersion")
final class ApiClientVersionSubstitutions {

  @Alias
  @RecomputeFieldValue(kind = Kind.Custom, declClass = ApiVersionTransformer.class)
  private static String DEFAULT_VERSION;

  private ApiClientVersionSubstitutions() {
  }

  static class ApiVersionTransformer implements CustomFieldValueTransformer {

    @Override
    public Object transform(
        jdk.vm.ci.meta.MetaAccessProvider metaAccess,
        jdk.vm.ci.meta.ResolvedJavaField original,
        jdk.vm.ci.meta.ResolvedJavaField annotated,
        Object receiver,
        Object originalValue) {
      String originalHeader = (String) originalValue;
      String[] tokens = originalHeader.split(" ");

      if (tokens.length > 0 && tokens[0].startsWith("gl-java")) {
        tokens[0] += "-graalvm";
        return String.join(" ", tokens);
      } else {
        return originalValue;
      }
    }
  }
}
