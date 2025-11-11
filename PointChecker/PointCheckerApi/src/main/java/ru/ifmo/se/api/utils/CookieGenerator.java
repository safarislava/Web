package ru.ifmo.se.api.utils;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieGenerator {
    public static ResponseCookie build(String name, String value, Duration expires) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(expires.getSeconds())
                .sameSite("Strict")
                .build();
    }
}
