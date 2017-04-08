#!/bin/sh

docker stop specmate
docker rm specmate
docker run -p 9876:8080 --name specmate -a stdin -a stdout -a stderr -i -t specmate/specmate:v0.1

