package ru.ifmo.se.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.services.JwtService;
import ru.ifmo.se.api.services.ShotService;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.dto.responses.ShotResponse;

import java.util.List;

@RestController
@RequestMapping("/api/shots")
@RequiredArgsConstructor
public class ShotsController {
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
    @Transactional
    public void clear(@CookieValue("accessToken") String token) {
        shotService.clearShots(jwtService.getUsername(token));
    }
}