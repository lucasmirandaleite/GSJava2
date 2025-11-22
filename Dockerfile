FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

# Copiar o JAR
COPY target/careermap-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
