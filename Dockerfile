FROM openjdk:11-jre-slim-buster
COPY target/time-me-0.0.1-SNAPSHOT.jar time-me-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/time-me-0.0.1-SNAPSHOT.jar"]