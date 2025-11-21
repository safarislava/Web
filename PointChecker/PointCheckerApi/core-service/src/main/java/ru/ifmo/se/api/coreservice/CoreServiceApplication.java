package ru.ifmo.se.api.coreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreServiceApplication {
    // TODO WebSockets, Oauth2
    public static void main(String[] args) {
        SpringApplication.run(CoreServiceApplication.class, args);
    }
}
