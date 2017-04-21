#!/bin/sh

cd ..
find . -name generated -exec rm -rf {} \;

cd bundles
sh gradlew clean
sh gradlew build
sh gradlew export
cd ../docker

cp ../bundles/specmate-std-env/generated/distributions/executable/specmate.jar .

docker build -t specmate/specmate:v0.1 .

rm specmate.jar
