FROM eclipse-temurin:17-jre AS api

WORKDIR /app

COPY target/UsersModule-1.0.0.jar /app/UsersModule.jar

EXPOSE 8002

CMD ["java", "-jar", "/app/UsersModule.jar"]
