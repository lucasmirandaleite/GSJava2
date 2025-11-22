FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar arquivos do projeto
COPY . .

# Instalar Maven e buildar
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# Executar aplicação
ENTRYPOINT ["java", "-jar", "target/gsjava2-0.0.1-SNAPSHOT.jar"]
