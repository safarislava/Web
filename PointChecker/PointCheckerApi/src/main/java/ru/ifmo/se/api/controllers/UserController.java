package ru.ifmo.se.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.api.services.JwtService;
import ru.ifmo.se.api.services.UserService;
import ru.ifmo.se.api.dto.requests.UserDto;
import ru.ifmo.se.api.utils.CookieGenerator;

import java.time.Duration;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody UserDto user) {
        userService.register(user);

        String username = user.getUsername();
        ResponseCookie accessCookie = CookieGenerator.build("accessToken",
                jwtService.generate(username, Duration.ofMinutes(15)), Duration.ofMinutes(15));
        ResponseCookie refreshCookie = CookieGenerator.build("refreshToken",
                jwtService.generate(username, Duration.ofDays(2)),  Duration.ofDays(2));

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).build();
    }
}
