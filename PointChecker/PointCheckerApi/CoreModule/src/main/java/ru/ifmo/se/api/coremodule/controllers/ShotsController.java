package ru.ifmo.se.api.coremodule.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import ru.ifmo.se.api.coremodule.components.JwtComponent;
import ru.ifmo.se.api.coremodule.components.PollListener;
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
    private final PollListener pollListener;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ShotResponse add(@CookieValue("accessToken") String token, @RequestBody ShotRequest request) {
        if (!jwtComponent.verify(token)) throw new UnAuthenticationException("Invalid token");
        return shotMessageService.sendAddShotRequest(request, jwtComponent.getUserId(token));
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

    @GetMapping(value = "/poll", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<List<ShotResponse>>> poll(@CookieValue("accessToken") String token) {
        if (!jwtComponent.verify(token)) throw new UnAuthenticationException("Invalid token");
        Long userId = jwtComponent.getUserId(token);

        DeferredResult<ResponseEntity<List<ShotResponse>>> pollResult = new DeferredResult<>(5000L);
        pollResult.onTimeout(() -> pollResult.setResult(ResponseEntity.noContent().build()));
        pollResult.onCompletion(() -> pollListener.removeListener(userId, pollResult));

        pollListener.addListener(userId, pollResult);
        return pollResult;
    }
}