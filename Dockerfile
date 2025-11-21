FROM maven:3.9.0-eclipse-temurin-11

WORKDIR /app

# Copiar arquivos do projeto
COPY pom.xml .
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Expor porta
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "target/careermap-0.0.1-SNAPSHOT.jar"]
