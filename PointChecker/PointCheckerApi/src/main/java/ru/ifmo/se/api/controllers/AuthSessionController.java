package ru.ifmo.se.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.exceptions.BadRequestException;
import ru.ifmo.se.api.services.JwtService;
import ru.ifmo.se.api.services.UserService;
import ru.ifmo.se.api.dto.requests.UserDto;
import ru.ifmo.se.api.utils.CookieGenerator;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth-sessions")
@RequiredArgsConstructor
public class AuthSessionController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> login(@RequestBody UserDto user) {
        boolean correct = userService.login(user.getUsername());
        if (!correct) throw new BadRequestException("Wrong username or password");
        return setCookies(user.getUsername());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> refresh(@CookieValue("refreshToken") String refreshToken) {
        String username = jwtService.getUsername(refreshToken);
        boolean correct = userService.login(username);
        if (!correct) throw new BadRequestException("Wrong username or password");
        return setCookies(username);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> logout() {
        ResponseCookie accessCookie = CookieGenerator.build("accessToken", "",  Duration.ofMinutes(15));
        ResponseCookie refreshCookie = CookieGenerator.build("refreshToken", "",   Duration.ofDays(2));

        // TODO blacklist tokens

        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).build();
    }

    private ResponseEntity<Void> setCookies(String username) {
        ResponseCookie accessCookie = CookieGenerator.build("accessToken",
                jwtService.generate(username, Duration.ofSeconds(10)), Duration.ofSeconds(10));
        ResponseCookie refreshCookie = CookieGenerator.build("refreshToken",
                jwtService.generate(username, Duration.ofDays(2)),  Duration.ofDays(2));

        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).build();
    }
}
