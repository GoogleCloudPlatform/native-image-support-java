# Cloud SQL Sample Application with Native Image

This application uses the [Google Cloud SQL JDBC Socket Factory](https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory) and is compatible with Native Image compilation.

The application runs some simple Cloud SQL queries to demonstrate compatibility.

## Setup Instructions

1. Follow the [GCP Project and Native Image Setup Instructions](../../README.md).

2.  Cloud SQL database setup:
  
    1. Follow [these instructions](https://cloud.google.com/sql/docs/mysql/create-instance) to create a Cloud SQL instance.
       Choose **MySQL** as the database provider because this sample is designed for Cloud SQL with MySQL.
       
    2. Navigate to your Cloud SQL instance from the [instances view page](https://console.cloud.google.com/sql/instances) and find the instance connection name.
       
       It should be of the form:
       ```
       {PROJECT_ID}:{REGION}:{SQL_INSTANCE_NAME}
       ```
       
       This string will be used to connect your instance.
    
    3. Follow [these instructions](https://cloud.google.com/sql/docs/mysql/create-manage-databases#create) to create a database.
       For this example, name the database `test_db`.
    
## Run with Native Image Compilation

1. Navigate to this directory and compile the application with the Native Image compiler.

    ```
    mvn package -P native
    ```

2. Run the application. Set the `-Dinstance` property to the instance connection name referenced above.

   ```
   ./target/cloud-sql-sample -Dinstance=<INSTANCE_CONNECTION_NAME>
   ```
   
   **Additional Properties**
   
   By default the application connects with the `root` user and an empty password to the `test_db` database.
   You may override the settings by passing additional arguments with the `-D` prefix to the executable:
   
   | Property        | Description                                 |
   | --------------- | ------------------------------------------- |
   | `-Dusername`    | Set the username to use. (Defaults to root) |
   | `-Dpassword`    | Set the password to use. (Defaults to "")   |
   | `-Dinstance`    | Specify the instance connection string.     |
   | `-Ddatabase`    | Specify the database to connect to.         |
   
3. The application will run through some basic operations to create a table and read records from it.

    ```
    [main] INFO com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
    Jun 04, 2021 2:28:13 PM com.google.cloud.sql.core.CoreSocketFactory connect
    INFO: Connecting to Cloud SQL instance [<your-instance-here>] via SSL socket.
    Books in database:
    a965870d-c148-4159-9280-81ea507af1f8, The Book
    ```
   
## Additional Notes

The Cloud SQL connector library uses the CP1252 charset when logging error messages from the server.

Therefore, you will need to pass `-H:+AddAllCharsets` to the Native Image compiler.

This is specified in the `buildArgs` section of the `native-maven-plugin` in the pom.xml of this sample.
