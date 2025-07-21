FROM openjdk:21-ea
MAINTAINER Fredy Sanabria
COPY target/api-0.0.1-SNAPSHOT.jar docker-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-api-0.0.1-SNAPSHOT.jar"]