FROM eclipse-temurin:17-jre AS api

WORKDIR /app

COPY target/shot-service-1.0.0.jar /app/shot-service.jar

EXPOSE 8003

CMD ["java", "-jar", "/app/shot-service.jar"]
