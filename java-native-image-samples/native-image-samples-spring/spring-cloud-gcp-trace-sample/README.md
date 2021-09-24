# Spring Cloud GCP Trace Example

This sample application demonstrates using [Spring Cloud GCP Trace Integration](https://github.com/spring-cloud/spring-cloud-gcp/blob/master/docs/src/main/asciidoc/trace.adoc) in an application using Native Image compilation.

Spring Cloud GCP is a set of integrations that makes it easier to use Google Cloud Platform services from the Spring Framework.
You can learn more by viewing the [Spring Cloud GCP reference documentation](https://spring.io/projects/spring-cloud-gcp).

## Setup & Configuration

1. Follow the [GCP Project Authentication and Native Image Setup Instructions](../../README.md).

2. Enable the [Cloud Trace APIs](https://console.cloud.google.com/apis/api/cloudtrace.googleapis.com/overview).

## Run with Native Image Compilation

1. Compile the application using the Native Image Compiler. This step may take a few minutes.

    ```
    mvn package -P native
    ```
    
2. Run the application:

    ```
    ./target/com.example.tracesampleapplication
    ```

3. Open a web browser and visit https://localhost:8080/.

    This will trigger the `ExampleController.work()` method, which in turn will call other services, and also a remote RESTful call to `ExampleController.meet()` method.

    To see the traces, navigate to [Cloud Traces List](https://console.cloud.google.com/traces/traces) in Google Cloud Console.
    You should see the trace information in detail.

### Run with Standard JVM

You can run the application under standard Java by running the following command:

```
mvn spring-boot:run
```
