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

import com.google.cloud.ServiceOptions;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Sample Quarkus Application demonstrating Cloud Pub/Sub operations.
 */
@Path("/")
public class QuarkusPubsubSampleApplication {

  private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();

  @Inject
  Template completed;

  /**
   * Creates a Pub/Sub topic.
   */
  @POST
  @Path("/createTopic")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance createTopic(
      @FormParam("topicName") String topicName) throws IOException {

    Topic topic = PubSubUtils.createTopic(topicName);
    String message = "Created topic: " + topic.getName() + " under project: " + PROJECT_ID;
    return completed.data("message", message);
  }

  /**
   * Creates a Pub/Sub subscription.
   */
  @POST
  @Path("/createSubscription")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance createSubscription(
      @FormParam("subscriptionName") String subscriptionName,
      @FormParam("topicName") String topicName) throws IOException {

    Subscription subscription = PubSubUtils.createSubscription(subscriptionName, topicName);
    String message = "Created pull subscription: " + subscription.getName();
    return completed.data("message", message);
  }

  /**
   * Subscribe to a Pub/Sub topic and poll a message if any are present.
   */
  @POST
  @Path("/subscribe")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance subscribe(
      @FormParam("subscription") String subscription) throws IOException {
    String payload = PubSubUtils.subscribeSync(subscription);
    return completed.data("message", "Message pulled from subscription: " + payload);
  }

  /**
   * Post a message to a Pub/Sub topic.
   */
  @POST
  @Path("/postMessage")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance postMessage(
      @FormParam("message") String payload,
      @FormParam("topicName") String topicName,
      @FormParam("count") int count) throws Exception {

    for (int i = 0; i < count; i++) {
      PubSubUtils.publishMessage(topicName, payload);
    }
    return completed.data("message", "Messages successfully published to topic.");
  }

  /**
   * Delete a Pub/Sub topic.
   */
  @POST
  @Path("/deleteTopic")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance deleteTopic(@FormParam("topic") String topic) throws Exception {
    TopicName topicName = PubSubUtils.deleteTopic(topic);
    return completed.data("message", "Deleted topic: " + topicName.getTopic());
  }

  /**
   * Delete a Pub/Sub subscription.
   */
  @POST
  @Path("/deleteSubscription")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance deleteSubscription(
      @FormParam("subscription") String subscription) throws Exception {

    ProjectSubscriptionName subscriptionName =
        PubSubUtils.deleteSubscription(subscription);
    return completed.data(
        "message", "Deleted subscription: " + subscriptionName.getSubscription());
  }
}
