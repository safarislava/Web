package ru.ifmo.se.api.pointchecker.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.pointchecker.controller.JwtBean;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {
    private final JwtBean jwtBean;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!request.getRequestURI().startsWith("/api/shots")) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<Cookie> authCookie = getAuthCookie(request);
        if (authCookie.isEmpty() || !jwtBean.verify(authCookie.get().getValue())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String token = authCookie.get().getValue();
            String username = jwtBean.getUsername(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Optional<Cookie> getAuthCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if  (cookies == null) return Optional.empty();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("accessToken")) {
                return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }
}