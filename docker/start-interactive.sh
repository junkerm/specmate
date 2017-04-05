#!/bin/sh

sudo docker stop specmate
sudo docker rm specmate
sudo docker run -p 9876:8080 --name specmate -a stdin -a stdout -a stderr -i -t specmate/specmate:v0.1

