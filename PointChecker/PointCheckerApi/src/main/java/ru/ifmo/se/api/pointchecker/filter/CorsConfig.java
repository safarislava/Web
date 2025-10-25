package ru.ifmo.se.api.pointchecker.filter;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.util.Set;

@Singleton
@Startup
public class CorsConfig {

    private Set<String> allowedOrigins;
    private boolean allowCredentials;
    private Set<String> allowedMethods;
    private Set<String> allowedHeaders;
    private Set<String> exposedHeaders;
    private int maxAge;

    @PostConstruct
    public void init() {
        this.allowedOrigins = Set.of(
                "http://localhost:4200",
                "http://127.0.0.1:4200"
        );
        this.allowCredentials = true;
        this.allowedMethods = Set.of("GET", "POST");
        this.allowedHeaders = Set.of("Origin", "Content-Type", "Accept", "Authorization",
                "X-Requested-With", "X-CSRF-Token", "Cache-Control"
        );
        this.exposedHeaders = Set.of("Set-Cookie");
        this.maxAge = 3600;
    }

    public Set<String> getAllowedOrigins() { return allowedOrigins; }
    public boolean isAllowCredentials() { return allowCredentials; }
    public Set<String> getAllowedMethods() { return allowedMethods; }
    public Set<String> getAllowedHeaders() { return allowedHeaders; }
    public Set<String> getExposedHeaders() { return exposedHeaders; }
    public int getMaxAge() { return maxAge; }
}