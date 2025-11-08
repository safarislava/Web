package ru.ifmo.se.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.exceptions.BadRequestException;
import ru.ifmo.se.api.services.JwtService;
import ru.ifmo.se.api.services.UserService;
import ru.ifmo.se.api.dto.requests.UserDto;

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
        if (!correct) throw new BadRequestException("Wrong username or password");

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
