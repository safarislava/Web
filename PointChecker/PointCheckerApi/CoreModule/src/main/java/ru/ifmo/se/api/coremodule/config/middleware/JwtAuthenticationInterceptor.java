package ru.ifmo.se.api.coremodule.config.middleware;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import ru.ifmo.se.api.coremodule.components.JwtComponent;

import jakarta.servlet.http.HttpServletRequest;
import ru.ifmo.se.api.coremodule.utils.CookieTokenManager;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationInterceptor implements HandshakeInterceptor {
    private final CookieTokenManager cookieTokenManager =  new CookieTokenManager();
    private final JwtComponent jwtComponent;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

            Optional<String> authToken = cookieTokenManager.getAuthTokenFromCookie(servletRequest);

            if (authToken.isPresent() && jwtComponent.verify(authToken.get())) {
                Long userId = jwtComponent.getUserId(authToken.get());
                attributes.put("userId", userId);
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
}