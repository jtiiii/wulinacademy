FROM openjdk:8-jre-alpine
MAINTAINER FuneralObjects <funeral_objects@qq.com>
RUN mkdir /app
VOLUME /app
EXPOSE 9000
WORKDIR /app
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","server.jar","--spring.profiles.active=prod"]