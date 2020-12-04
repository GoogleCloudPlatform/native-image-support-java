# Pub/Sub Sample Application with GraalVM

The Pub/Sub sample application demonstrates some common operations with Pub/Sub and is compatible with GraalVM compilation.

## Setup Instructions

1. Follow the [GCP Project and GraalVM Setup Instructions](../../README.md).

2. [Enable the Pub/Sub APIs](https://console.cloud.google.com/apis/api/pubsub.googleapis.com).

### Run with GraalVM Compilation

Navigate to this directory in a new terminal.

1. Compile the application using the GraalVM Compiler. This step may take a few minutes.

    ```
    mvn package -P graal
    ```
    
2. Run the application:

    ```
    ./target/com.example.pubsubsampleapplication
    ```

3. The application will create a new Pub/Sub topic, send and receive a message from it, and then delete the topic.

    ```
    Created topic: projects/YOUR_PROJECT_ID/topics/graal-pubsub-test-00e72640-4e36-4aff-84d2-13b7569b2289 under project: YOUR_PROJECT_ID
    Created pull subscription: projects/YOUR_PROJECT_ID/subscriptions/graal-pubsub-test-sub2fb5e3f3-cb26-439b-b88c-9cb0cfca9e45
    Published message with ID: 457327433078420
    Received Payload: Pub/Sub Graal Test published message at timestamp: 2020-09-23T19:45:42.746514Z
    Deleted topic projects/YOUR_PROJECT_ID/topics/graal-pubsub-test-00e72640-4e36-4aff-84d2-13b7569b2289
    Deleted subscription projects/YOUR_PROJECT_ID/subscriptions/graal-pubsub-test-sub2fb5e3f3-cb26-439b-b88c-9cb0cfca9e45
    ```
