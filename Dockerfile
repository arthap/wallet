FROM openjdk:8-jdk-alpine
MAINTAINER experto.com
VOLUME /tmp
EXPOSE 8081
ADD build/libs/springbootdemo-0.0.1-SNAPSHOT.jar springbootdemo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/springbootdemo.jar"]
