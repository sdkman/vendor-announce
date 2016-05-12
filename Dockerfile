FROM java:8

MAINTAINER Marco Vermeulen

RUN mkdir /announce

ADD build/libs /announce

ENTRYPOINT java -jar /announce/application.jar

