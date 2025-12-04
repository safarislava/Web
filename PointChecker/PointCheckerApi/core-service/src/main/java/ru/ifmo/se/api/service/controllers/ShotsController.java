package ru.ifmo.se.api.service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.common.dto.shot.ShotRequest;
import ru.ifmo.se.api.common.dto.shot.ShotResponse;
import ru.ifmo.se.api.common.dto.user.TokenClaimsResponse;
import ru.ifmo.se.api.service.services.ShotMessageService;
import ru.ifmo.se.api.service.services.TokenMessageService;

import java.util.List;

@RestController
@RequestMapping("/api/shots")
@RequiredArgsConstructor
public class ShotsController {
    private final TokenMessageService tokenMessageService;
    private final ShotMessageService shotMessageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ShotResponse add(@CookieValue("accessToken") String token, @RequestBody ShotRequest request) {
        TokenClaimsResponse claims = tokenMessageService.getTokenClaims(token);
        ShotResponse response = shotMessageService.sendAddShotRequest(request, claims.getUserId());
        System.out.println(response.getId().toString());
        return response;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ShotResponse> getPoints(@CookieValue("accessToken") String token) {
        TokenClaimsResponse claims = tokenMessageService.getTokenClaims(token);
        return shotMessageService.sendGetShotsRequest(claims.getUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void clear(@CookieValue("accessToken") String token) {
        TokenClaimsResponse claims = tokenMessageService.getTokenClaims(token);
        shotMessageService.sendClearShotsRequest(claims.getUserId());
    }
}