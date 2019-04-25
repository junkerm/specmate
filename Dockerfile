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
RUN mkdir /opt/specmate/src

COPY web /opt/specmate/src/web
COPY bundles /opt/specmate/src/bundles

WORKDIR /opt/specmate/src/web
RUN LIBSASS_EXT="no" npm run init
RUN npm run build-prod

WORKDIR /opt/specmate/src/bundles
RUN sed -i 's/\r$//' ./gradlew
RUN sed -i 's/^M$//' ./gradlew
RUN ./gradlew --version
RUN ./gradlew --no-daemon clean build export -x check

RUN cp specmate-std-env/generated/distributions/executable/dev-specmate-all.jar /opt/specmate/specmate.jar

WORKDIR /

RUN rm -rf /opt/specmate/src

CMD java -Djdk.crypto.KeyAgreement.legacyKDF=true -jar /opt/specmate/specmate.jar --configurationFile /opt/specmate/conf/specmate-config.properties
