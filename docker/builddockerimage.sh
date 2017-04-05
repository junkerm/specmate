#!/bin/sh

cd ..
find . -name generated -exec rm -rf {} \;

cd bundles
sh gradlew build
sh gradlew export
cd ../docker

cp ../bundles/specmate-std-env/generated/distributions/executable/specmate.jar .

sudo docker build -t specmate/specmate:v0.1 .

rm specmate.jar
