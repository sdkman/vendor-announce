FROM java:8

MAINTAINER Marco Vermeulen

RUN mkdir /announce

ADD build/libs /announce

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /announce/application.jar

