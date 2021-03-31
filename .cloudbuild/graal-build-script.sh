#!/bin/sh

gu install native-image
./mvnw clean install -B -q
