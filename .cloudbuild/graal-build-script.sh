#!/bin/sh

gu install native-image
./mvnw clean install -B -q
./mvnw verify -T 3 -P graal -B --file google-cloud-graalvm-samples