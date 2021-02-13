# Cloud Tasks Sample Application with GraalVM

The Cloud Tasks sample application demonstrates some common operations with [Google Cloud Tasks](https://cloud.google.com/tasks) and is compatible with GraalVM compilation.

This application will create a new queue called `graal-test-queue` if it does not already exist.
It will then submit a new task to this queue.

## Setup Instructions

1. Follow the [GCP Project and GraalVM Setup Instructions](../../README.md).

2. [Enable the Cloud Tasks APIs](https://console.cloud.google.com/apis/api/cloudtasks.googleapis.com).

### Run with GraalVM Compilation

Navigate to this directory in a new terminal.

1. Compile the application using the GraalVM Compiler. This step may take a few minutes.

    ```
    mvn package -P graal
    ```
    
2. Run the application:

    ```
    ./target/com.example.taskssampleapplication
    ```

3. The application runs through some basic Cloud Tasks operations (create queue, create task) and then prints some results of the operations.

    ```

    ```
