package ru.ifmo.se.api.pointchecker.filter;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Getter
@Configuration
public class CorsConfig {
    private final Set<String> allowedOrigins;
    private final boolean allowCredentials;
    private final Set<String> allowedMethods;
    private final Set<String> allowedHeaders;
    private final Set<String> exposedHeaders;
    private final int maxAge;

    public CorsConfig() {
        this.allowedOrigins = Set.of(
                "http://localhost:4200",
                "http://185.239.141.48:4200",
                "http://localhost");
        this.allowCredentials = true;
        this.allowedMethods = Set.of("GET", "POST", "DELETE", "OPTIONS");
        this.allowedHeaders = Set.of("Origin", "Content-Type", "Accept", "Authorization",
                "X-Requested-With", "X-CSRF-Token", "Cache-Control", "Cookie", "Set-Cookie"
        );
        this.exposedHeaders = Set.of("Set-Cookie");
        this.maxAge = 3600;
    }
}