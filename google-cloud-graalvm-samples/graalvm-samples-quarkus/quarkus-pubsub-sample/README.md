# Quarkus Pub/Sub Sample Application with GraalVM

The Quarkus Pub/Sub sample application demonstrates some common operations with Google Cloud Pub/Sub and is compatible with GraalVM compilation.

This application is built using the [Quarkus Framework](https://quarkus.io/).

The application will show a simple user interface that lets you create and delete Cloud Pub/Sub resources, read messages from topics, and publish messages to topics.

![screenshot of the application](app_screenshot.png)

## Setup Instructions

1. Follow the [GCP Project and GraalVM Setup Instructions](../../README.md).
You may skip the GraalVM installation step if you would like to build the executable in a container.

2. [Enable the Pub/Sub APIs](https://console.cloud.google.com/apis/api/pubsub.googleapis.com).

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```
mvn quarkus:dev
```

Then visit http://localhost:8080/ to view the application.

## Creating a native executable

### Compiling using local GraalVM installation

You can create a native executable using the `graal` profile.

```
mvn package -P native`
```

### Compiling using Docker container

If you don't have GraalVM installed, you can run the native executable build in a container using the following command.

```
mvn package -P native -Dquarkus.native.container-build=true -Dnative-image.xmx=6g
```

This method requires Docker Engine to be installed and configured to have at least 6 GB of memory.
Look for Docker -> Preferences -> Resources -> Advanced to set the memory limit.
You may use this method on any machine that supports Docker, but it will produce an executable that will only run on Linux.

### Running the executable on Linux

You can then execute your native executable with: `./target/quarkus-pubsub-sample-0.7.0-SNAPSHOT-runner`

Then visit http://localhost:8080/ to view the application.

### Running the executable on non-Linux (MacOS / Windows) using Docker

You will need to [create](https://cloud.google.com/iam/docs/creating-managing-service-account-keys#iam-service-account-keys-create-console) a service account key if you don't have one.
Then you will be able to run the executable in a Linux container.

```
$ export CREDS_PATH=/PATH/TO/CREDS/DIRECTORY/
$ export CREDS_FILE_NAME=credentials-file.json
$ docker run -it --rm -v $PWD/target/:/target/ -v $CREDS_PATH:/creds/ --env GOOGLE_APPLICATION_CREDENTIALS=/creds/$CREDS_FILE_NAME -p 8080:8080 ubuntu ./target/quarkus-pubsub-sample-0.7.0-SNAPSHOT-runner
```

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.