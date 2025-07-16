# Stage 1: Build
FROM maven:3.9.5-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests=false

FROM openjdk:17
ENV PROFILE=host


# Stage 2: Run
FROM eclipse-temurin:17-jdk

COPY --from=build /app_gateway/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dserver.port=8080","-Dspring.profiles.active=${PROFILE}", "app.jar"]
