FROM java:8

MAINTAINER Maximilian Junker <junkerm@in.tum.de>

RUN apt-get update && apt-get install -y unzip

COPY ../bundles/specmate-std-env/generated/distributions/executable/specmate.jar specmate.jar

CMD java -jar specmate.jar
