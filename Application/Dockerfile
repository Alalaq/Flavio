#syntax=docker/dockerfile:experimental
FROM maven:3.6.3-openjdk-17-slim AS build
COPY src /home/application/src
COPY pom.xml /home/application/
USER root
RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests=true -f /home/application/pom.xml clean package

FROM openjdk:17
COPY --from=build /home/application/target/flavio.jar user/local/lib/flavio.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","user/local/lib/flavio.jar"]