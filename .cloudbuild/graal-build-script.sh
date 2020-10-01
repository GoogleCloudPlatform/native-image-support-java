#!/bin/sh

gu install native-image
echo ${JAVA_HOME}
java --version
./mvnw clean install -B -q
./mvnw package -P graal -B --file google-cloud-graalvm-samples/
./google-cloud-graalvm-samples/google-cloud-graalvm-pubsub-sample/target/com.example.pubsubsampleapplication