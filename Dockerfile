FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o arquivo JAR (ajuste o nome conforme seu projeto)
COPY target/*.jar app.jar

# Expose da porta (ajuste conforme sua aplicação)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
