# Cloud Trace Client Libraries with Native Image

This application uses the Google Cloud [Trace Client Libraries](https://github.com/googleapis/java-trace) and can be compiled with Native Image Native Image.

**Note:** In practice, the Trace Client Libraries are not used directly but through another tool, such as through the [OpenCensus Cloud Trace integration](https://cloud.google.com/trace/docs/setup/java) or through a framework like Spring via [Spring Cloud GCP Trace](https://github.com/spring-cloud/spring-cloud-gcp/blob/master/docs/src/main/asciidoc/trace.adoc).

## Setup Instructions

1. Follow the [GCP Project and Native Image Setup Instructions](../../README.md).

2. Enable the [Cloud Trace APIs](https://console.cloud.google.com/apis/api/cloudtrace.googleapis.com/overview).

## Run with Native Image Compilation

Navigate to this directory in a new terminal.
   
1. Compile the application using the Native Image Compiler. This step may take a few minutes.

   ```
   mvn package -P native
   ```
   
2. Run the application:

   ```
   ./target/com.example.tracesampleapplication
   ```

3. The application will generate a new trace and send the data to Cloud Trace where it will be viewable in the [Google Cloud Console Traces Viewer](https://console.cloud.google.com/traces/traces).

    ```
    Wait some time for the Trace to be populated.
    Retrieved trace: 1be886734c6a4053adc4346b2b9040c5
    It has the following spans: 
    Span: native-image-trace-sample-test
    ```
