package ru.ifmo.se.api.service.config.middleware;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import ru.ifmo.se.api.service.models.StompPrincipal;
import ru.ifmo.se.api.service.services.TokenMessageService;
import ru.ifmo.se.api.service.utils.CookieTokenManager;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtHandshakeHandler extends DefaultHandshakeHandler {
    private final TokenMessageService tokenMessageService;
    private final CookieTokenManager cookieTokenManager = new CookieTokenManager();

    @Override
    protected Principal determineUser(
            @NonNull ServerHttpRequest request,
            @NonNull WebSocketHandler wsHandler,
            @NonNull Map<String, Object> attributes
    ) {
        try {
            if (request instanceof ServletServerHttpRequest servletServerHttpRequest) {
                Optional<String> authToken = cookieTokenManager.getAuthTokenFromCookie(servletServerHttpRequest.getServletRequest());
                if (authToken.isEmpty()) return null;
                Long userId = tokenMessageService.getTokenClaims(authToken.get()).getUserId();
                return new StompPrincipal(userId.toString());
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
