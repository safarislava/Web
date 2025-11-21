package ru.ifmo.se.api.service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import ru.ifmo.se.api.common.shotservice.ShotRequest;
import ru.ifmo.se.api.service.components.PollListener;
import ru.ifmo.se.api.common.shotservice.ShotResponse;
import ru.ifmo.se.api.common.userservice.TokenClaimsResponse;
import ru.ifmo.se.api.service.services.ShotMessageService;
import ru.ifmo.se.api.service.services.TokenMessageService;

import java.util.List;

@RestController
@RequestMapping("/api/shots")
@RequiredArgsConstructor
public class ShotsController {
    private final TokenMessageService tokenMessageService;
    private final ShotMessageService shotMessageService;
    private final PollListener pollListener;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ShotResponse add(@CookieValue("accessToken") String token, @RequestBody ShotRequest request) {
        TokenClaimsResponse claims = tokenMessageService.getTokenClaims(token);
        return shotMessageService.sendAddShotRequest(request, claims.getUserId());
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

    @GetMapping(value = "/poll", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<List<ShotResponse>>> poll(@CookieValue("accessToken") String token) {
        TokenClaimsResponse claims = tokenMessageService.getTokenClaims(token);

        DeferredResult<ResponseEntity<List<ShotResponse>>> pollResult = new DeferredResult<>(10000L);
        pollResult.onTimeout(() -> pollResult.setResult(ResponseEntity.noContent().build()));
        pollResult.onCompletion(() -> pollListener.removeListener(claims.getUserId(), pollResult));

        pollListener.addListener(claims.getUserId(), pollResult);
        return pollResult;
    }
}