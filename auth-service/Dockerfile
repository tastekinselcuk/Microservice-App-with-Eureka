FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/auth-service-0.0.1-SNAPSHOT.jar .
COPY src/main/resources/log4j2.xml /app/log4j2.xml

CMD ["java", "-jar", "auth-service-0.0.1-SNAPSHOT.jar"]
