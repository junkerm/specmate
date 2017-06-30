FROM java:8

MAINTAINER Maximilian Junker <junkerm@in.tum.de>

RUN apt-get update && apt-get install -y unzip

COPY specmate.jar specmate.jar
COPY specmate-config.properties specmate-config.properties

CMD java -jar specmate.jar --configurationFile specmate-config.properties
