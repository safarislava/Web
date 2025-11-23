package ru.ifmo.se.api.service.config.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.dto.user.TokensDto;
import ru.ifmo.se.api.service.services.UserMessageService;
import ru.ifmo.se.api.service.utils.CookieTokenManager;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserMessageService userMessageService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        TokensDto tokens = userMessageService.sendSyncRequest(email);

        List<ResponseCookie> cookies = CookieTokenManager.setCookies(tokens);
        for (ResponseCookie cookie : cookies) {
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }

        getRedirectStrategy().sendRedirect(request, response, "http://localhost:4200/main");
    }
}