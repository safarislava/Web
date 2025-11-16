package ru.ifmo.se.api.coremodule.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.ifmo.se.api.coremodule.components.ShotSocketHandler;
import ru.ifmo.se.api.coremodule.config.middleware.JwtAuthenticationInterceptor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final ShotSocketHandler shotSocketHandler;
    private final JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(shotHandler(), "/ws/shots")
                .addInterceptors(jwtAuthenticationInterceptor)
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler shotHandler() {
        return shotSocketHandler;
    }
}
