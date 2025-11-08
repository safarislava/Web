package ru.ifmo.se.api.pointchecker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.pointchecker.services.JwtService;
import ru.ifmo.se.api.pointchecker.services.ShotService;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;
import ru.ifmo.se.api.pointchecker.dto.ShotResponse;

import java.util.List;

@RestController
@RequestMapping("/api/shots")
@RequiredArgsConstructor
public class ShotsContoller {
    private final ShotService shotService;
    private final JwtService jwtService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@CookieValue("accessToken") String token, @RequestBody ShotRequest request) {
        shotService.addShot(request, jwtService.getUsername(token));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShotResponse> getPoints(@CookieValue("accessToken") String token) {
        return shotService.getShotResponses(jwtService.getUsername(token));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void clear(@CookieValue("accessToken") String token) {
        shotService.clearShots(jwtService.getUsername(token));
    }
}