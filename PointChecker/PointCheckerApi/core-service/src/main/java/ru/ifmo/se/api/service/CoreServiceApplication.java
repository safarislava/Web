package ru.ifmo.se.api.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreServiceApplication {
    // TODO WebSockets(BFF), Oauth2
    public static void main(String[] args) {
        SpringApplication.run(CoreServiceApplication.class, args);
    }
}
