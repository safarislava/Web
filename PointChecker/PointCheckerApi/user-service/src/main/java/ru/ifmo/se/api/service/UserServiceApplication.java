package ru.ifmo.se.api.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
/*
services:
  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
    restart: unless-stopped

  point-checker-core-module:
    build:
      context: CoreModule
      dockerfile: Dockerfile
    environment:
      RABBITMQ_HOST: rabbitmq
    container_name: point-checker-core-module
    restart: unless-stopped
    ports:
      - "8001:8001"
    depends_on:
      - rabbitmq

  points-checker-users-module:
    build:
      context: UsersModule
      dockerfile: Dockerfile
    environment:
      RABBITMQ_HOST: rabbitmq
      DB_PASSWORD: ckfdecbr13579
      DB_USERNAME: s467570
      DB_URL: jdbc:postgresql://185.239.141.48:5432/pointsdb
      SECRET: aa946dd9-5240-4103-86ca-2873e751fa9e
    container_name: point-checker-users-module
    restart: unless-stopped
    ports:
      - "8002:8002"
    depends_on:
      - rabbitmq

  points-checker-shots-module:
    build:
      context: ShotsModule
      dockerfile: Dockerfile
    environment:
      RABBITMQ_HOST: rabbitmq
      DB_PASSWORD: ckfdecbr13579
      DB_USERNAME: s467570
      DB_URL: jdbc:postgresql://185.239.141.48:5432/pointsdb
      SECRET: aa946dd9-5240-4103-86ca-2873e751fa9e
    container_name: point-checker-shots-module
    restart: unless-stopped
    ports:
      - "8003:8003"
    depends_on:
      - rabbitmq


 */