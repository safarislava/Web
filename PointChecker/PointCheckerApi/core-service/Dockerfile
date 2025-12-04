FROM eclipse-temurin:17-jre AS api

WORKDIR /app

COPY target/core-service-1.0.0.jar /app/core-service.jar

EXPOSE 8001

CMD ["java", "-jar", "/app/core-service.jar"]
