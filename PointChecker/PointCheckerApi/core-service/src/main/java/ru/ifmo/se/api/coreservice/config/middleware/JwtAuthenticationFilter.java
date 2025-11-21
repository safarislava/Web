package ru.ifmo.se.api.coreservice.config.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ifmo.se.api.coreservice.services.TokenMessageService;
import ru.ifmo.se.api.coreservice.services.UserMessageService;
import ru.ifmo.se.api.coreservice.utils.CookieTokenManager;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenMessageService tokenMessageService;
    private final UserMessageService userMessageService;
    private final CookieTokenManager cookieTokenManager =  new CookieTokenManager();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        Optional<String> authToken = cookieTokenManager.getAuthTokenFromCookie(request);
        if (authToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId;
        List<String> roles;
        try {
            userId = tokenMessageService.getTokenClaims(authToken.get()).getUserId();
            roles = userMessageService.sendGetRolesRequest(userId);
        }
        catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            List<GrantedAuthority> authorities = roles.stream()
                    .filter(role -> role != null && !role.trim().isEmpty())
                    .map(role -> "ROLE_" + role.toUpperCase())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId,null, authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }
}