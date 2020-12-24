# BigTable Sample Application with GraalVM

This application uses the [Google Cloud BigTable Client Libraries](https://cloud.google.com/bigtable/docs/reference/libraries) and is compatible with GraalVM compilation.

The application runs through some simple BigTable Client Library operations to demonstrate compatibility.

## Setup Instructions

1. Follow the [GCP Project and GraalVM Setup Instructions](../../README.md).

2.  BigTable Environment setup -
    The following sections describe how you can run the sample application against the BigTable emulator or a real BigTable instance.
  
    1. *(Using emulator)* If you wish to run the application against the [BigTable emulator](https://cloud.google.com/bigtable/docs/emulator), make sure that you have the [Google Cloud SDK](https://cloud.google.com/sdk) installed.

       In a new terminal window, start the emulator via `gcloud`:
    
       ```
       gcloud beta emulators bigtable start --host-port=localhost:9010
       ```
   
       Leave the emulator running in this terminal for now.
       In the next section, we will run the sample application against the BigTable emulator instance.
    
    2. *(Using real BigTable instance)* If instead you wish to run the application against a real BigTable instance, you must create a BigTable instance named `graalvm-test-instance` in your project.

       ```
       gcloud bigtable instances create graalvm-test-instance \
           --cluster=graalvm-test-cluster \
           --cluster-zone=us-central1-c \
           --cluster-num-nodes=1 \
           --display-name=graalvm-test-instance
       ```
       
       You can also manually manage your BigTable resources through the [BigTable Cloud Console view](http://console.cloud.google.com/bigtable).
    
## Run with GraalVM Compilation

1. Navigate to this directory and compile the application with the GraalVM compiler.

    ```
    mvn package -P graal
    ```

2. **(Optional)** If you're using the emulator, export the `BIGTABLE_EMULATOR_HOST` as an environment variable in your terminal.
   
    ```
    export BIGTABLE_EMULATOR_HOST=localhost:9010
    ``` 
   
    The BigTable Client Libraries will detect this environment variable and automatically connect to the emulator instance if this variable is set.
    
3. Run the application.
    
    ```
    ./target/com.example.bigtablesampleapplication
    ```

4. The application will run through some basic BigTable operations and log some output statements.

    ```
    Created table: graalvm-test-table2b5b0031-f4ea-4c39-bc0c-bf6c3c62c90c
    Successfully wrote row: phone#1608775178843000
    Reading phone data in table: 
    Key: phone#1608775178843000
        connected_cell:  @1608775178843000
        connected_wifi:  @1608775178843000
        os_build: PQ2A.190405.003 @1608775178843000
    Deleted table: graalvm-test-table2b5b0031-f4ea-4c39-bc0c-bf6c3c62c90c
    ```