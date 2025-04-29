FROM openjdk:25-jdk-slim

WORKDIR /app

COPY target/recipe-back.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]