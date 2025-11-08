package ru.ifmo.se.api.pointchecker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.pointchecker.services.JwtService;
import ru.ifmo.se.api.pointchecker.services.UserService;
import ru.ifmo.se.api.pointchecker.dto.UserDto;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth-sessions")
@RequiredArgsConstructor
public class AuthSessionController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> login(@RequestBody UserDto userDto) {
        boolean correct = userService.login(userDto);
        if (!correct) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String token = jwtService.generate(userDto);
        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
