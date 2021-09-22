#!/bin/sh

gu install native-image
./mvnw clean install --batch-mode --quiet
./mvnw verify --activate-profiles native \
    --threads 3 \
    --batch-mode \
    --quiet \
    --projects \!com.example:cloud-functions-sample \
    --file java-native-image-samples
