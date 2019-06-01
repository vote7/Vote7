FROM maven:3.6-jdk-11-slim AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src src
RUN mvn package spring-boot:repackage

FROM openjdk:11-jdk-slim
WORKDIR /app

COPY --from=build /app/target/vote7-1.0-SNAPSHOT.jar .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "vote7-1.0-SNAPSHOT.jar"]