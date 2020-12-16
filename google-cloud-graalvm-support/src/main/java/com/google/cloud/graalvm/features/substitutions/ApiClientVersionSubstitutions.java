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
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;
import com.oracle.svm.core.annotate.TargetElement;

/**
 * Substitution for setting Java version correctly in the Google Java Http Client.
 */
@TargetClass(className =
    "com.google.api.client.googleapis.services.AbstractGoogleClientRequest$ApiClientVersion")
final class ApiClientVersionSubstitutions {

  @Alias
  private String versionString;

  @Substitute
  @TargetElement(name = "<init>")
  ApiClientVersionSubstitutions(
      String javaVersion, String osName, String osVersion, String clientVersion) {
    StringBuilder sb = new StringBuilder("gl-java/");
    sb.append(formatSemver(javaVersion));
    sb.append("-graalvm");
    sb.append(" gdcl/");
    sb.append(formatSemver(clientVersion));
    if (osName != null && osVersion != null) {
      sb.append(" ");
      sb.append(formatName(osName));
      sb.append("/");
      sb.append(formatSemver(osVersion));
    }
    this.versionString = sb.toString();
  }

  @Substitute
  private static String formatSemver(String version) {
    throw new UnsupportedOperationException(
        "This is a method alias for the substitution. Should not be called.");
  }

  @Substitute
  private static String formatName(String name) {
    throw new UnsupportedOperationException(
        "This is a method alias for the substitution. Should not be called.");
  }
}
