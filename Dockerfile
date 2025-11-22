FROM eclipse-temurin:11-jdk-alpine

WORKDIR /app

# Debug: listar arquivos
RUN echo "=== LISTING FILES ==="
RUN ls -la

# Copiar arquivos
COPY . .

# Debug: verificar se mvnw existe
RUN echo "=== CHECKING MVNW ==="
RUN ls -la mvnw*
RUN chmod +x mvnw

# Debug: verificar Maven version
RUN echo "=== MAVEN VERSION ==="
RUN ./mvnw --version

# Executar build
RUN echo "=== BUILDING ==="
RUN ./mvnw clean package -DskipTests

# Debug: verificar se JAR foi criado
RUN echo "=== CHECKING TARGET ==="
RUN ls -la target/
RUN find . -name "*.jar"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/careermap-0.0.1-SNAPSHOT.jar"]
