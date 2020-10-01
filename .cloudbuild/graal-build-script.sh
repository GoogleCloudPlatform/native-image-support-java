#!/bin/sh

gu install native-image
./mvnw clean install package -B -q -P graal
./google-cloud-graalvm-samples/google-cloud-graalvm-pubsub-sample/target/com.example.pubsubsampleapplication