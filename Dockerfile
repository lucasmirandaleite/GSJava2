FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

# Copiar qualquer JAR do target
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
