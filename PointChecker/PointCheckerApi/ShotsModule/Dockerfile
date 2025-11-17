FROM eclipse-temurin:17-jre AS api

WORKDIR /app

COPY target/ShotsModule-1.0.0.jar /app/ShotsModule.jar

EXPOSE 8003

CMD ["java", "-jar", "/app/ShotsModule.jar"]
