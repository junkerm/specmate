FROM ubuntu:18.04

RUN apt-get --fix-missing update
RUN apt-get -y install wget software-properties-common

RUN mkdir /opt/adoptopenjdk
WORKDIR /opt/adoptopenjdk

ENV JDKFILE=OpenJDK11U-jdk_x64_linux_hotspot_11.0.3_7.tar.gz
ENV JDKNAME=jdk-11.0.3+7
ENV JDKDIR=jdk-11.0.3+7
ENV JDKVERSION=11
RUN wget --quiet https://github.com/AdoptOpenJDK/openjdk$JDKVERSION-binaries/releases/download/$JDKNAME/$JDKFILE
RUN tar -xf $JDKFILE
RUN rm $JDKFILE
ENV PATH="/opt/adoptopenjdk/${JDKDIR}/bin:${PATH}"

RUN mkdir /opt/specmate
RUN mkdir /opt/specmate/database
RUN mkdir /opt/specmate/conf

COPY bundles/specmate-std-env/generated/distributions/executable/dev-specmate-all.jar /opt/specmate/specmate.jar

WORKDIR /

CMD java -Djdk.crypto.KeyAgreement.legacyKDF=true -jar /opt/specmate/specmate.jar --configurationFile /opt/specmate/conf/specmate-config.properties
