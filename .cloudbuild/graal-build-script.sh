#!/bin/sh

gu install native-image
./mvnw clean install --batch-mode --quiet
./mvnw verify --threads 3 -X \
    --activate-profiles graal \
    --batch-mode \
    --file google-cloud-graalvm-samples
