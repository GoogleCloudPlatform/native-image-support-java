# Secret Manager Sample Application with Native Image

The Secret Manager sample application demonstrates some common operations with [Google Cloud Secret Manager](https://cloud.google.com/secret-manager) and is compatible with Native Image compilation.

This application will create a new secret named `graal-secretmanager-test-secret` if it does not already exist.
It will then add a new version of the secret and then attempt to read it.

## Setup Instructions

1. Follow the [GCP Project and Native Image Setup Instructions](../../README.md).

2. [Enable the Secret Manager APIs](https://console.cloud.google.com/apis/api/secretmanager.googleapis.com).

### Run with Native Image Compilation

Navigate to this directory in a new terminal.

1. Compile the application using the Native Image Compiler. This step may take a few minutes.

    ```
    mvn package -P native
    ```
    
2. Run the application:

    ```
    ./target/secretmanager-sample
    ```

3. The application runs through some basic Secret Manager operations (create, update, read) and then prints some results of the operations.

    ```
    Created secret: projects/xxxxxx/secrets/graal-secretmanager-test-secret
    Added Secret Version: projects/xxxxxx/secrets/graal-secretmanager-test-secret/versions/1
    Reading secret value: Hello World
    (Note: Don't print secret values in prod!)
    ```
