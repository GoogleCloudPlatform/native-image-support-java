#!/bin/sh

gu install native-image
./mvnw clean install -B -q
./mvnw verify -T 4 -P graal -B --file google-cloud-graalvm-samples