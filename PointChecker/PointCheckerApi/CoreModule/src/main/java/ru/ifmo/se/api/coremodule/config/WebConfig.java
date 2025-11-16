package ru.ifmo.se.api.coremodule.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200", "http://localhost",
                        "http://185.239.141.48", "http://172.18.0.3")
                .allowedMethods("POST", "GET", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(Duration.ofDays(14).getSeconds());
    }
}
