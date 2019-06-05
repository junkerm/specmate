FROM ubuntu:18.04

RUN apt-get --fix-missing update
RUN apt-get -y install wget software-properties-common

RUN mkdir /opt/adoptopenjdk
WORKDIR /opt/adoptopenjdk
ENV JDKFILE=OpenJDK8U-jdk_x64_linux_openj9_8u212b03_openj9-0.14.0.tar.gz
ENV JDKNAME=jdk8u212-b03_openj9-0.14.0
ENV JDKDIR=jdk8u212-b03
ENV JDKVERSION=8
RUN wget --quiet https://github.com/AdoptOpenJDK/openjdk$JDKVERSION-binaries/releases/download/$JDKNAME/$JDKFILE
RUN tar -xf $JDKFILE
RUN rm $JDKFILE
ENV PATH="/opt/adoptopenjdk/${JDKDIR}/bin:${PATH}"

RUN mkdir /opt/specmate
RUN mkdir /opt/specmate/database
RUN mkdir /opt/specmate/conf

RUN cp bundles/specmate-std-env/generated/distributions/executable/dev-specmate-all.jar /opt/specmate/specmate.jar

WORKDIR /

CMD java -Djdk.crypto.KeyAgreement.legacyKDF=true -jar /opt/specmate/specmate.jar --configurationFile /opt/specmate/conf/specmate-config.properties
