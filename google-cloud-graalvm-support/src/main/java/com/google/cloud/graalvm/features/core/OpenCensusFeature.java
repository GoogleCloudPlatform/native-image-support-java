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

import static com.google.cloud.graalvm.features.NativeImageUtils.registerForReflectiveInstantiation;

import com.oracle.svm.core.annotate.AutomaticFeature;
import org.graalvm.nativeimage.hosted.Feature;

/**
 * Registers reflection usage in OpenCensus libraries.
 */
@AutomaticFeature
public class OpenCensusFeature implements Feature {

  private static final String OPEN_CENSUS_CLASS = "io.opencensus.impl.tags.TagsComponentImpl";

  @Override
  public void beforeAnalysis(BeforeAnalysisAccess access) {
    if (access.findClassByName(OPEN_CENSUS_CLASS) != null) {
      registerForReflectiveInstantiation(
          access, "io.opencensus.impl.metrics.MetricsComponentImpl");
      registerForReflectiveInstantiation(
          access, "io.opencensus.impl.tags.TagsComponentImpl");
      registerForReflectiveInstantiation(
          access, "io.opencensus.impl.trace.TraceComponentImpl");
    }
  }
}
