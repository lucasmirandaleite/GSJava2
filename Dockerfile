FROM eclipse-temurin:11-jre
WORKDIR /app
COPY target/careermap-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8080
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
