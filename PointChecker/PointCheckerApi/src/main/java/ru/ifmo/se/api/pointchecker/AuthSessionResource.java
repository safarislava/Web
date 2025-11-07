package ru.ifmo.se.api.pointchecker;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.Cookie;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.pointchecker.controller.JwtBean;
import ru.ifmo.se.api.pointchecker.controller.UserBean;
import ru.ifmo.se.api.pointchecker.dto.UserDto;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth-sessions")
@RequiredArgsConstructor
public class AuthSessionResource {
    private final UserBean userBean;
    private final JwtBean jwtBean;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> login(@RequestBody UserDto userDto) {
        boolean correct = userBean.login(userDto);
        if (!correct) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String token = jwtBean.generate(userDto);
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
