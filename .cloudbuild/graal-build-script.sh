#!/bin/sh

gu install native-image
./mvnw clean install --batch-mode --quiet
./mvnw verify --activate-profiles graal \
    --threads 3 \
    --batch-mode \
    --quiet \
    --file google-cloud-graalvm-samples
