# Cloud Functions Proof of concept

Simple application demonstrating running Cloud Functions with GraalVM.

## Overview

See the `pom.xml` for the setup. 

All functions can be run in non-native mode by running `mvn function:run` using the Cloud Function plugin.
Just change the `<functionTarget>` configuration in the `pom.xml` to change which function to invoke.

## Native Image

This sample builds a native image of the function by including the Cloud Functions Framework (Invoker library and Functions API) and the user's source code into the native image.

### Instructions

1. Install GraalVM.
The easiest way to download GraalVM is to use [SDKMAN](https://sdkman.io/) which manages Java versions for you and allows you to easily switch between GraalVM/non-GraalVM Java.
Once installed, run `gu install native-image`.

    Run `java -version` and check the output:
    
    ```
    openjdk version "11.0.10" 2021-01-19
    OpenJDK Runtime Environment GraalVM CE 21.0.0 (build 11.0.10+8-jvmci-21.0-b06)
    OpenJDK 64-Bit Server VM GraalVM CE 21.0.0 (build 11.0.10+8-jvmci-21.0-b06, mixed mode, sharing)
    ```

2. Run `mvn package -P native` in this directory.
This triggers the native image build.
The resulting image is outputted to the `target/` directory.

3. Try running the functions.

    a. **Background Function**: This simulates running a GCF Pub/Sub Background function.
    
    Run this command to invoke the image: 
    ```
    ./target/com.google.cloud.functions.invoker.runner.invoker --target com.example.function.PubSubBackgroundFunction
    ```
    
    In a new terminal window, send a POST request to the service to simulate receiving a Background event:
    
    ```
    curl localhost:8080 -d "{'data':{'data':'hello world', 'attributes': {'attr1':1, 'attr2':2}}}"`
    ```
    
    You will see the Background function be invoked:
    
    ```
    Background function run.
    Received payload:
    PubSubMessage{data='hello world', attributes={attr1=1, attr2=2}}
    ```
   
    b. **HTTP Function:** There's a couple simple `HttpFunction` examples:
        
      * `com.example.function.BasicHttpFunction` - Responds with a basic message.
      * `com.example.function.ErrorHttpFunction` - Responds with error code 500.
      * `com.example.function.PubSubHttpFunction` - Sends a Pub/Sub message to topic `test-topic` when invoked.
      
    You can run the examples by invoking the same command.
    Just change the function to run with `--target`:
    
    ```
    ./target/com.google.cloud.functions.invoker.runner.invoker --target com.example.function.PubSubHttpFunction
    ```
    
    Once it's running, visit `localhost:8080` to verify it works.
    The `PubSubHttpFunction` will publish a message to a topic named `test-topic` once invoked, so you should create the topic in your GCP project if you want to try it out.
