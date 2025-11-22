FROM eclipse-temurin:11-jdk-alpine

WORKDIR /app

# Instalar Maven
RUN apk add --no-cache maven

# Copiar arquivos
COPY . .

# Build com Maven instalado
RUN mvn clean package -DskipTests

# Encontrar e usar o JAR
RUN JAR_FILE=$(find target -name "*.jar" | head -n 1) && \
    echo "JAR found: $JAR_FILE" && \
    cp "$JAR_FILE" app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
