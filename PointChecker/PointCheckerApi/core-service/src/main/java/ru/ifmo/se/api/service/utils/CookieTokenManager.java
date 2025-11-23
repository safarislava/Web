package ru.ifmo.se.api.service.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import ru.ifmo.se.api.common.dto.user.TokensDto;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CookieTokenManager {
    public static Optional<String> getAuthTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    public static List<ResponseCookie> setCookies(TokensDto tokensDto) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", tokensDto.getAccessToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMinutes(5).getSeconds())
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokensDto.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/api/users")
                .maxAge(Duration.ofDays(2).getSeconds())
                .sameSite("Lax")
                .build();

        return List.of(accessCookie, refreshCookie);
    }

    public static ResponseEntity.HeadersBuilder<?> setCookies(ResponseEntity.HeadersBuilder<?> response, TokensDto tokensDto) {
        List<ResponseCookie> cookies = setCookies(tokensDto);
        for (ResponseCookie cookie : cookies) {
            response.header(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        return response;
    }

    public static ResponseEntity.HeadersBuilder<?> clearCookies(ResponseEntity.HeadersBuilder<?> response) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMinutes(0).getSeconds())
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/users")
                .maxAge(Duration.ofDays(0).getSeconds())
                .sameSite("Lax")
                .build();

        ResponseCookie jsessionCookie = ResponseCookie.from("JSESSIONID", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return response
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jsessionCookie.toString());
    }
}
