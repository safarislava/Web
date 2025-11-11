package ru.ifmo.se.api.config.middleware;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.ifmo.se.api.services.JwtService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        Optional<String> authToken = getAuthTokenFromCookie(request);
        if (authToken.isEmpty() || !jwtService.verify(authToken.get())) return true;

        String username = jwtService.getUsername(authToken.get());
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return true;
    }

    private Optional<String> getAuthTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return Optional.empty();

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}