FROM eclipse-temurin:17-jre AS api

WORKDIR /app

COPY target/bff-service-1.0.0.jar /app/bff-service.jar

EXPOSE 8004

CMD ["java", "-jar", "/app/bff-service.jar"]