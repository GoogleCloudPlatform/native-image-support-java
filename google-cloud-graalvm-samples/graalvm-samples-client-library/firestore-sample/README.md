# Firestore Sample Application with GraalVM

This application uses the [Google Cloud Firestore client libraries](https://cloud.google.com/firestore/docs/quickstart-servers#java) and is compatible with GraalVM compilation.

This sample runs through basic operations of creating a new document, running queries, and then deleting the created resources.

## Setup Instructions

1. Follow the [GCP Project and GraalVM Setup Instructions](../../README.md).

2. If you wish to run the application against the [Firestore emulator](https://cloud.google.com/sdk/gcloud/reference/beta/emulators/firestore), make sure that you have the [Google Cloud SDK](https://cloud.google.com/sdk) installed.

    In a new terminal window, start the emulator via `gcloud`:
    
    ```
    gcloud beta emulators firestore start --host-port=localhost:9010
    ```
   
    Leave the emulator running in this terminal for now.
    In the next section, we will run the sample application against the Firestore emulator instsance.
    
## Run with GraalVM Compilation

1. Navigate to this directory and compile the application with the GraalVM compiler.

    ```
    mvn package -P graal
    ```

2. **(Optional)** If you're using the emulator, export the `FIRESTORE_EMULATOR_HOST` as an environment variable in your terminal.
   
    ```
    export FIRESTORE_EMULATOR_HOST=localhost:9010
    ``` 
   
    The Firestore Client Libraries will detect this environment variable and automatically connect to the emulator instance if this variable is set.
    
3. Run the application.
    
    ```
    ./target/com.example.firestoresampleapplication
    ```

4. The application will run through some basic Firestore operations and log some output statements.

    ```
    Created user alovelace. Timestamp: 2020-12-15T20:19:28.444070000Z
    The following users were saved:
    Document: alovelace | Ada Lovelace born 1815
    Number of users born before 1900: 1
    Number of users born earlier after 1900: 0
    Number of users whose first name is 'Ada': 0
    ```