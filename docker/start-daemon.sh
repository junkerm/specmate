#!/bin/sh
docker stop specmate
docker rm specmate
sudo docker run -p 8080:8080 --name specmate -d -t specmate/specmate:v0.1

