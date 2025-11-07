package ru.ifmo.se.api.pointchecker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.pointchecker.controller.JwtBean;
import ru.ifmo.se.api.pointchecker.controller.ShotBean;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;
import ru.ifmo.se.api.pointchecker.dto.ShotResponse;

import java.util.List;

@RestController
@RequestMapping("/api/shots")
@RequiredArgsConstructor
public class ShotsResource {
    private final ShotBean shotBean;
    private final JwtBean jwtBean;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@CookieValue("accessToken") String token, @RequestBody ShotRequest request) {
        shotBean.addShot(request, jwtBean.getUsername(token));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShotResponse> getPoints(@CookieValue("accessToken") String token) {
        return shotBean.getShotResponses(jwtBean.getUsername(token));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void clear(@CookieValue("accessToken") String token) {
        shotBean.clearShots(jwtBean.getUsername(token));
    }
}