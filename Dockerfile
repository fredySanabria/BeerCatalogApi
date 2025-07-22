FROM openjdk:21-ea
MAINTAINER Fredy Sanabria
COPY target/api-1.0.0.jar docker-api-1.0.0.jar
ENTRYPOINT ["java","-jar","/docker-api-1.0.0.jar"]