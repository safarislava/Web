FROM eclipse-temurin:21
EXPOSE 24571
RUN mkdir /app
WORKDIR /app
COPY ../fastcgi-server/target/fastcgi-server-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar
ENTRYPOINT ["java", "-DFCGI_PORT=24571", "-jar", "app.jar"]