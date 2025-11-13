package ru.ifmo.se.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.exceptions.BadRequestException;
import ru.ifmo.se.api.services.JwtService;
import ru.ifmo.se.api.services.UserService;
import ru.ifmo.se.api.dto.requests.UserDto;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody UserDto userDto) {
        userService.register(userDto);
        userService.setLastUpdate(userDto.getUsername()); // IDK
        ResponseEntity.HeadersBuilder<?> response = ResponseEntity.noContent();
        return setCookies(response, userDto.getUsername()).build();
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> login(@RequestBody UserDto userDto) {
        if (!userService.login(userDto)) throw new BadRequestException("Wrong username or password");

        userService.setLastUpdate(userDto.getUsername()); // IDK
        ResponseEntity.HeadersBuilder<?> response = ResponseEntity.noContent();
        return setCookies(response, userDto.getUsername()).build();
    }

    @GetMapping(path = "/refresh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) throw new BadRequestException("Refresh token is empty");
        if (!jwtService.verify(refreshToken)) throw new BadRequestException("Invalid refresh token");

        Instant creationTime = jwtService.getIssuedTime(refreshToken);
        Instant updateTime = userService.getLastUpdate(jwtService.getUsername(refreshToken));
        if (creationTime.plus(Duration.ofMinutes(1)).isBefore(updateTime)) throw new BadRequestException("Wrong refresh token");

        String username = jwtService.getUsername(refreshToken);
        userService.setLastUpdate(username);
        ResponseEntity.HeadersBuilder<?> response = ResponseEntity.noContent();
        return setCookies(response, username).build();
    }

    @DeleteMapping(path = "/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> logout(@CookieValue(value = "accessToken") String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) throw new BadRequestException("Access token is empty");
        if (!jwtService.verify(accessToken)) throw new BadRequestException("Wrong refresh token");

        String username = jwtService.getUsername(accessToken);
        userService.setLastUpdate(username);

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMinutes(5).getSeconds())
                .sameSite("Strict")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/users")
                .maxAge(Duration.ofDays(2).getSeconds())
                .sameSite("Strict")
                .build();

        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).build();
    }

    private ResponseEntity.HeadersBuilder<?> setCookies(ResponseEntity.HeadersBuilder<?> response, String username) {
        ResponseCookie accessCookie = ResponseCookie.from(
                "accessToken", jwtService.generate(username, Duration.ofMinutes(5)))
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMinutes(5).getSeconds())
                .sameSite("Strict")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from(
                "refreshToken", jwtService.generate(username, Duration.ofDays(2)))
                .httpOnly(true)
                .secure(false)
                .path("/api/users")
                .maxAge(Duration.ofDays(2).getSeconds())
                .sameSite("Strict")
                .build();

        return response.header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }
}
