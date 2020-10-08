# Pub/Sub Sample Application with GraalVM

The Pub/Sub sample application demonstrates some common operations with Pub/Sub and is compatible with GraalVM compilation.

## Setup Instructions

1. Follow the [GCP Project Authentication and GraalVM Setup Instructions](../README.md).

2. [Enable the Cloud Storage APIs](https://console.cloud.google.com/apis/api/storage.googleapis.com).

### Run with GraalVM Compilation

Navigate to this directory in a new terminal.

1. Compile the application using the GraalVM Compiler. This step may take a few minutes.

    ```
    mvn package -P graal
    ```
    
2. Run the application:

    ```
    ./target/com.example.storagesampleapplication
    ```

3. The application will run through basic Cloud Storage operations of creating, reading, and deleting Cloud Storage resources.

    You can manually manage Cloud Storage resources through [Google Cloud Console](https://console.cloud.google.com/storage).

    ```
    Creating bucket graalvm-sample-bucket-7221f161-688c-4a7a-9120-8900d20f0802
    Write file to bucket.
    Reading the file that was written...
    Successfully wrote to file: Hello World!
    Cleaning up resources...
    Deleted file graalvm-sample-file-5d927aaf-cb03-41de-8383-696733893db5
    Deleted bucket graalvm-sample-bucket-7221f161-688c-4a7a-9120-8900d20f0802
   ```
