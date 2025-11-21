package ru.ifmo.se.api.coreservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.common.userservice.TokensDto;
import ru.ifmo.se.api.common.userservice.UserDto;
import ru.ifmo.se.api.coreservice.services.UserMessageService;

import java.time.Duration;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserMessageService messageService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody UserDto userDto) {
        TokensDto tokensDto = messageService.sendRegisterUserRequest(userDto);

        ResponseEntity.HeadersBuilder<?> response = ResponseEntity.noContent();
        return setCookies(response, tokensDto).build();
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> login(@RequestBody UserDto userDto) {
        TokensDto tokensDto = messageService.sendLoginUserRequest(userDto);

        ResponseEntity.HeadersBuilder<?> response = ResponseEntity.noContent();
        return setCookies(response, tokensDto).build();
    }

    @GetMapping(path = "/refresh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        TokensDto tokensDto = messageService.sendRefreshUserRequest(new TokensDto(null, refreshToken));

        ResponseEntity.HeadersBuilder<?> response = ResponseEntity.noContent();
        return setCookies(response, tokensDto).build();
    }

    @DeleteMapping(path = "/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> logout(@CookieValue(value = "accessToken") String accessToken) {
        messageService.sendLogoutUserRequest(new TokensDto(accessToken, null));

        ResponseEntity.HeadersBuilder<?> response = ResponseEntity.noContent();
        return clearCookies(response).build();
    }

    private ResponseEntity.HeadersBuilder<?> setCookies(ResponseEntity.HeadersBuilder<?> response, TokensDto tokensDto) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", tokensDto.getAccessToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMinutes(5).getSeconds())
                .sameSite("Strict")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokensDto.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/api/users")
                .maxAge(Duration.ofDays(2).getSeconds())
                .sameSite("Strict")
                .build();

        return response.header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    private ResponseEntity.HeadersBuilder<?> clearCookies(ResponseEntity.HeadersBuilder<?> response) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMinutes(0).getSeconds())
                .sameSite("Strict")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/users")
                .maxAge(Duration.ofDays(0).getSeconds())
                .sameSite("Strict")
                .build();

        return response.header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }
}
