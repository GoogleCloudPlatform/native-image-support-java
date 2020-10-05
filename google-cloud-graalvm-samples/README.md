# Google Cloud GraalVM Samples

This directory contains code samples that depend on the [Google Java Client Libraries](https://github.com/googleapis/google-cloud-java) and are compatible with GraalVM native image compilation.

## Setup Instructions

You will need to follow these prerequisite steps in order to run these samples:

1. If you have not already, [create a Google Cloud Platform Project](https://cloud.google.com/resource-manager/docs/creating-managing-projects#creating_a_project). 

2. Install the [Google Cloud SDK](https://cloud.google.com/sdk/) which will allow you to run the sample with your project's credentials.

    Once installed, log in with Application Default Credentials using the following command:
    
    ```
    gcloud auth application-default login
    ```
   
    **Note:** Authenticating with Application Default Credentials is convenient to use during development, but we recommend [alternate methods of authentication](https://cloud.google.com/docs/authentication/production) during production use.
    
3. Install the GraalVM compiler.
    
    You can follow the [official installation instructions](https://www.graalvm.org/docs/getting-started-with-graalvm/#install-graalvm) from the GraalVM website.
    After following the instructions, ensure that you install the Native Image extension installed by running:
    
    ```
    gu install native-image
    ```
   
    Once you finish following the instructions, verify that the default version of Java is set to the Graal version by running `java -version` in a terminal.
    
    You will see something similar to the below output:
    
    ```
    $ java -version
   
    openjdk version "11.0.7" 2020-04-14
    OpenJDK Runtime Environment GraalVM CE 20.1.0 (build 11.0.7+10-jvmci-20.1-b02)
    OpenJDK 64-Bit Server VM GraalVM CE 20.1.0 (build 11.0.7+10-jvmci-20.1-b02, mixed mode, sharing)
    ```
