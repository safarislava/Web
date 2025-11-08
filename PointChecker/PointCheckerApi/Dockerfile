FROM eclipse-temurin:17-jre AS api

WORKDIR /app

COPY target/PointChecker.jar /app/PointChecker.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/PointChecker.jar"]
