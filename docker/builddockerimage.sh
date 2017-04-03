#!/bin/sh

sh ../bundles/gradlew export

sudo docker build -t specmate/specmate:v0.1 .

