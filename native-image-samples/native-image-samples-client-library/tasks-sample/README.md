# Cloud Tasks Sample Application with Native Image

The Cloud Tasks sample application demonstrates some common operations with [Google Cloud Tasks](https://cloud.google.com/tasks) and is compatible with Native Image compilation.

This application will create a new queue called `graal-test-queue` if it does not already exist.
It will then submit a new task to this queue.

## Setup Instructions

1. Follow the [GCP Project and Native Image Setup Instructions](../../README.md).

2. [Enable the Cloud Tasks APIs](https://console.cloud.google.com/apis/api/cloudtasks.googleapis.com).

### Run with Native Image Compilation

Navigate to this directory in a new terminal.

1. Compile the application using the Native Image Compiler. This step may take a few minutes.

    ```
    mvn package -P native
    ```
    
2. Run the application:

    ```
    ./target/com.example.taskssampleapplication
    ```

3. The application runs through some basic Cloud Tasks operations (create queue, create task) and then prints some results of the operations.

    ```
   Test queue ready: name: "projects/xxxxxxxxxx/locations/us-central1/queues/graal-test-queue-4009"
   rate_limits {
   max_dispatches_per_second: 500.0
   max_burst_size: 100
   max_concurrent_dispatches: 1
   }
   retry_config {
   max_attempts: 100
   min_backoff {
   nanos: 100000000
   }
   max_backoff {
   seconds: 3600
   }
   max_doublings: 16
   }
   state: RUNNING
   
   Created task: name: "projects/xxxxxxxxxx/locations/us-central1/queues/graal-test-queue-4009/tasks/5886258204485021611"
   http_request {
   url: "https://google.com/"
   http_method: GET
   headers {
   key: "User-Agent"
   value: "Google-Cloud-Tasks"
   }
   }
   schedule_time {
   seconds: 1613189391
   nanos: 486293000
   }
   create_time {
   seconds: 1613189391
   }
   dispatch_deadline {
   seconds: 600
   }
   view: BASIC
   
   Queue purged
   Queue deleted
    ```
