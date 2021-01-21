# Datastore Sample Application with GraalVM

This application uses the [Google Cloud Datastore client libraries](https://cloud.google.com/datastore/docs/reference/libraries) and is compatible with GraalVM compilation.

This sample runs through some basic operations of creating/deleting entities, running queries, and running transaction code.

## Setup Instructions

1. Follow the [GCP Project and GraalVM Setup Instructions](../../README.md).

2. If you wish to run the application against the [Datastore emulator](https://cloud.google.com/sdk/gcloud/reference/beta/emulators/datastore), make sure that you have the [Google Cloud SDK](https://cloud.google.com/sdk) installed.

    In a new terminal window, start the emulator via `gcloud`:
    
    ```
    gcloud beta emulators datastore start --host-port=localhost:9010
    ```
   
    Leave the emulator running in this terminal for now.
    In the next section, we will run the sample application against the Datastore emulator instsance.
    
## Run with GraalVM Compilation

1. Navigate to this directory and compile the application with the GraalVM compiler.

    ```
    mvn package -P graal
    ```

2. **(Optional)** If you're using the emulator, export the `DATASTORE_EMULATOR_HOST` as an environment variable in your terminal.
   
    ```
    export DATASTORE_EMULATOR_HOST=localhost:9010
    ``` 
   
    The Datastore Client Libraries will detect this environment variable and automatically connect to the emulator instance if this variable is set.
    
3. Run the application.
    
    ```
    ./target/com.example.datastoresampleapplication
    ```

4. The application will run through some basic Datastore operations and log some output statements.

    ```
    Successfully added entity.
    Reading entity: f806384f-259d-4d04-8c69-1ae0d83c063c
    Successfully deleted entity: f806384f-259d-4d04-8c69-1ae0d83c063c
    Run dummy transaction code.
    Found entity: Entity{key=Key{projectId=my-kubernetes-codelab-217414, namespace=graalvm-test-namespace, path=[PathElement{kind=test-kind, id=null, name=2cbd912e-8074-4430-802d-32e60102afb2}]}, properties={description=StringValue{valueType=STRING, excludeFromIndexes=false, meaning=0, value=hello world}}}
    Ran transaction callable.
    ```