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

package com.example;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.NativeImageTest;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * This executes the same tests in {@link QuarkusPubsubSampleApplicationTest} but in native image
 * mode.
 */
@NativeImageTest
public class NativeQuarkusPubsubSampleApplicationIT extends QuarkusPubsubSampleApplicationTest {

  @Test
  public void validateHeaders() throws IOException {
    String headers =
        given()
            .when().get("/headers")
            .body().print();

    String[] headerTokens = headers.split(" ");
    assertThat(headerTokens).hasSizeGreaterThan(0);
    assertThat(headerTokens[0]).startsWith("gl-java");
    assertThat(headerTokens[0]).endsWith("-graalvm");
  }
}
