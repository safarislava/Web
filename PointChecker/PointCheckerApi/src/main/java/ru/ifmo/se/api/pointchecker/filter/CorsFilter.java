package ru.ifmo.se.api.pointchecker.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.StringJoiner;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class CorsFilter implements Filter {
    private final CorsConfig corsConfig;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");

        if (origin != null && isOriginAllowed(origin)) {
            response.setHeader("Access-Control-Allow-Origin",
                    corsConfig.isAllowCredentials() ? origin : "*");

            if (corsConfig.isAllowCredentials()) {
                response.setHeader("Access-Control-Allow-Credentials", "true");
            }

            response.setHeader("Access-Control-Allow-Methods", joinSet(corsConfig.getAllowedMethods()));
            response.setHeader("Access-Control-Allow-Headers", joinSet(corsConfig.getAllowedHeaders()));
            response.setHeader("Access-Control-Expose-Headers", joinSet(corsConfig.getExposedHeaders()));
            response.setHeader("Access-Control-Max-Age", String.valueOf(corsConfig.getMaxAge()));
        }

        if ("OPTIONS".equalsIgnoreCase(request.getMethod()) && origin != null && isOriginAllowed(origin)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    private boolean isOriginAllowed(String origin) {
        return corsConfig.getAllowedOrigins().contains(origin) ||
                corsConfig.getAllowedOrigins().contains("*");
    }

    private String joinSet(Set<String> set) {
        StringJoiner joiner = new StringJoiner(", ");
        set.forEach(joiner::add);
        return joiner.toString();
    }
}