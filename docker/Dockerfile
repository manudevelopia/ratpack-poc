FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY .env ./
COPY build/libs/ratpack-poc-all.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
