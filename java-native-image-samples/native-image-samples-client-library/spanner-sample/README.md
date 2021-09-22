# Spanner Sample Application with Native Image

This is a sample application which uses the Cloud Spanner client libraries and demonstrates compatibility with native image compilation.

The application creates a new Spanner instance and database, and it runs basic operations including queries and Spanner mutations.

## Setup Instructions

1. Follow the [GCP Project and Native Image Setup Instructions](../../README.md).

2. If you wish to run the application against the [Spanner emulator](https://cloud.google.com/spanner/docs/emulator), make sure that you have the [Google Cloud SDK](https://cloud.google.com/sdk) installed.

    In a new terminal window, start the emulator via `gcloud`:
    
    ```
    gcloud beta emulators spanner start
    ```
   
    You may leave the emulator running for now.
    In the next section, we will run the sample application against the Spanner emulator instsance.
    
## Run with Native Image Compilation

1. Navigate to this directory and compile the application with the Native Image compiler.

    ```
    mvn package -P native
    ```

2. **(Optional)** If you're using the emulator, export the `SPANNER_EMULATOR_HOST` as an environment variable in your terminal.
   
    ```
    export SPANNER_EMULATOR_HOST=localhost:9010
    ``` 
   
    The Spanner Client Libraries will detect this environment variable and will automatically connect to the emulator instance if this variable is set.
    
3. Run the application.
    
    ```
    ./target/com.example.spannersampleapplication 
    ```

4. The application will run through some basic Spanner operations and log some output statements.

    ```
    Running the Spanner Sample.
    Singers Registered in Spanner:
    Bob Loblaw
    Virginia Watson
    ```