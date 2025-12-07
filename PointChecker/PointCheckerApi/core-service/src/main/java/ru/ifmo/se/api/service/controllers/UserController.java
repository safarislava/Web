package ru.ifmo.se.api.service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.common.dto.user.TokensDto;
import ru.ifmo.se.api.common.dto.user.UserDto;
import ru.ifmo.se.api.service.services.UserMessageService;
import ru.ifmo.se.api.service.utils.CookieTokenManager;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserMessageService messageService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody UserDto userDto) {
        TokensDto tokensDto = messageService.sendRegisterUserRequest(userDto);

        ResponseEntity.HeadersBuilder<?> response = ResponseEntity.noContent();
        return CookieTokenManager.setCookies(response, tokensDto).build();
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> login(@RequestBody UserDto userDto) {
        TokensDto tokensDto = messageService.sendLoginUserRequest(userDto);

        ResponseEntity.HeadersBuilder<?> response = ResponseEntity.noContent();
        return CookieTokenManager.setCookies(response, tokensDto).build();
    }

    @GetMapping(path = "/refresh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        TokensDto tokensDto = messageService.sendRefreshUserRequest(new TokensDto(null, refreshToken));

        ResponseEntity.HeadersBuilder<?> response = ResponseEntity.noContent();
        return CookieTokenManager.setCookies(response, tokensDto).build();
    }

    @DeleteMapping(path = "/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> logout(@CookieValue(value = "accessToken") String accessToken) {
        messageService.sendLogoutUserRequest(new TokensDto(accessToken, null));

        ResponseEntity.HeadersBuilder<?> responseBuilder = ResponseEntity.noContent();
        return CookieTokenManager.clearCookies(responseBuilder).build();
    }
}
