package ru.ifmo.se.api.coremodule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.coremodule.components.JwtComponent;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.ShotRequest;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.ShotResponse;
import ru.ifmo.se.api.coremodule.exceptions.UnAuthenticationException;
import ru.ifmo.se.api.coremodule.services.ShotMessageService;
import java.util.List;

@RestController
@RequestMapping("/api/shots")
@RequiredArgsConstructor
public class ShotsController {
    private final JwtComponent jwtComponent;
    private final ShotMessageService shotMessageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@CookieValue("accessToken") String token, @RequestBody ShotRequest request) {
        if (!jwtComponent.verify(token)) throw new UnAuthenticationException("Invalid token");
        shotMessageService.sendAddShotRequest(request, jwtComponent.getUserId(token));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ShotResponse> getPoints(@CookieValue("accessToken") String token) {
        if (!jwtComponent.verify(token)) throw new UnAuthenticationException("Invalid token");
        return shotMessageService.sendGetShotsRequest(jwtComponent.getUserId(token));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void clear(@CookieValue("accessToken") String token) {
        if (!jwtComponent.verify(token)) throw new UnAuthenticationException("Invalid token");
        shotMessageService.sendClearShotsRequest(jwtComponent.getUserId(token));
    }
}