FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar apenas o JAR (assumindo que o build é feito pelo Railway)
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
