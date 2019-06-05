FROM ubuntu:18.04

RUN apt-get update
RUN apt-get -y install curl software-properties-common build-essential
RUN curl -sL https://deb.nodesource.com/setup_10.x | bash -
RUN add-apt-repository -y ppa:webupd8team/java
RUN apt-get update

RUN apt-get -y install nodejs
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y --force-yes --no-install-recommends oracle-java8-installer

RUN mkdir /opt/specmate
RUN mkdir /opt/specmate/database
RUN mkdir /opt/specmate/conf

RUN cp bundles/specmate-std-env/generated/distributions/executable/dev-specmate-all.jar /opt/specmate/specmate.jar

WORKDIR /

CMD java -Djdk.crypto.KeyAgreement.legacyKDF=true -jar /opt/specmate/specmate.jar --configurationFile /opt/specmate/conf/specmate-config.properties
