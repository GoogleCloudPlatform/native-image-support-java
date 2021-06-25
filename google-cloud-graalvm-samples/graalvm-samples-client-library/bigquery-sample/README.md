# BigQuery Sample Application with GraalVM

The BigQuery sample application demonstrates some common operations with [Google Cloud BigQuery](https://cloud.google.com/bigquery) and is compatible with GraalVM compilation.

## Setup Instructions

1. Follow the [GCP Project Authentication and GraalVM Setup Instructions](../../README.md).

2. [Enable the BigQuery APIs](https://console.cloud.google.com/apis/api/bigquery.googleapis.com).

### Run with GraalVM Compilation

Navigate to this directory in a new terminal.

1. Compile the application using the GraalVM Compiler. This step may take a few minutes.

    ```
    mvn package -P native
    ```
    
2. Run the application:

    ```
    ./target/com.example.bigquerysampleapplication
    ```

3. The application will create a sample BigQuery dataset in your GCP project called `graalvm_test_dataset` and perform some simple operations like creating a table, inserting data, and running a query.

    If you would like to delete the BigQuery dataset later, you can manage your BigQuery resources through [Google Cloud Console](https://console.cloud.google.com/bigquery) to clean up BigQuery resources under your project.

    When you run the application, you'll see output like this in the terminal:
    
    ```
    Created new table: graalvm_test_table_2351b0891d2f48af9309bd289c3bad13
    Successfully inserted test row.
    Queried the following records: 
    User id: TestUser-2f39e3ec-d81a-483f-9ec0-b9bd54155710 | age: 40
    Deleted table: graalvm_test_table_2351b0891d2f48af9309bd289c3bad13
   ```
