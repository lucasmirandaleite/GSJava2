FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar apenas o JAR
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
