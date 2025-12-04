FROM eclipse-temurin:17-jre AS api

WORKDIR /app

COPY target/user-service-1.0.0.jar /app/user-service.jar

EXPOSE 8002

CMD ["java", "-jar", "/app/user-service.jar"]
