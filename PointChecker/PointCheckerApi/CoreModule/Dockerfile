FROM eclipse-temurin:17-jre AS api

WORKDIR /app

COPY target/CoreModule-1.0.0.jar /app/CoreModule.jar

EXPOSE 8001

CMD ["java", "-jar", "/app/CoreModule.jar"]
