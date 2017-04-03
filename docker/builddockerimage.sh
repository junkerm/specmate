#!/bin/sh

cd ../bundles
sh ../bundles/gradlew export
cd ../docker

cp ../bundles/specmate-std-env/generated/distributions/executable/specmate.jar .

sudo docker build -t specmate/specmate:v0.1 .

rm specmate.jar
