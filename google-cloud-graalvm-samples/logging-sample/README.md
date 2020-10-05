# Cloud Logging Client Libraries with GraalVM

This application uses the Google Cloud [Logging Client Libraries](https://github.com/googleapis/java-logging) and can be compiled with GraalVM Native Image.

## Setup Instructions

1. Follow the [GCP Project and GraalVM Setup Instructions](../README.md).

2. Enable the [Logging APIs](https://console.cloud.google.com/flows/enableapi?apiid=logging.googleapis.com).

## Run with GraalVM Compilation

Navigate to this directory in a new terminal.

1. Compile the application using the GraalVM Compiler. This step may take a few minutes.

   ```
   mvn package -P graal
   ```

2. Run the application:

   ```
   ./target/com.example.loggingsampleapplication
   ```

3. The application will log a message. Navigate to the [Cloud Console Logs Viewer](https://console.cloud.google.com/logs/viewer) to view the newly generated log entry.

    ```
    Log message written to Cloud Logging.
    See your logs in the Cloud Console: https://console.cloud.google.com/logs/viewer
    ```
