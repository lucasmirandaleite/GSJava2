# Use imagem oficial mais simples
FROM openjdk:17-slim

# Diretório de trabalho
WORKDIR /app

# Copiar arquivos do Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Dar permissão de execução ao mvnw
RUN chmod +x mvnw

# Copiar código fonte
COPY src ./src

# Compilar o projeto
RUN ./mvnw clean package -DskipTests

# Expor porta
EXPOSE 8080

# Comando para executar
CMD ["java", "-jar", "target/gsjava2-0.0.1-SNAPSHOT.jar"]
