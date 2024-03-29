/*
 * Copyright 2020-2021 Google LLC
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The busy-work service for the sample application.
 *
 * @author Ray Tsang
 */
@Service
public class WorkServiceImpl implements WorkService {
  private static final Log LOGGER = LogFactory.getLog(WorkServiceImpl.class);

  private final RestTemplate restTemplate;

  public WorkServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Simulates work that may be triggered by sending a request to an external microservice.
   */
  @NewSpan
  public void visitMeetEndpoint(String meetUrl) {
    LOGGER.info("starting busy work");
    for (int i = 0; i < 3; i++) {
      this.restTemplate.getForObject(meetUrl, String.class);
    }
    LOGGER.info("finished busy work");
  }
}
