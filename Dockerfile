FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar arquivos do projeto
COPY . .

# Instalar Maven se necessário e buildar
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# Verificar o nome exato do JAR gerado
RUN find /app/target -name "*.jar"

# Executar aplicação (ajuste o nome do JAR conforme necessário)
ENTRYPOINT ["java", "-jar", "target/gsjava2-0.0.1-SNAPSHOT.jar"]
