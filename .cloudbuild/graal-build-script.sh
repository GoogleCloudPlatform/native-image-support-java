#!/bin/sh

gu install native-image
./mvnw clean install -B -q
./mvnw package -P graal -B --file google-cloud-graalvm-samples/
./google-cloud-graalvm-samples/pubsub-sample/target/com.example.pubsubsampleapplication
