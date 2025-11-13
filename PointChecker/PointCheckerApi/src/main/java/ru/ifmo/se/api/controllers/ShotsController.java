package ru.ifmo.se.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.api.components.RequestProcessor;
import ru.ifmo.se.api.mappers.ShotMapper;
import ru.ifmo.se.api.models.Shot;
import ru.ifmo.se.api.services.JwtService;
import ru.ifmo.se.api.services.ShotService;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.dto.responses.ShotResponse;

import java.util.List;

@RestController
@RequestMapping("/api/shots")
@RequiredArgsConstructor
public class ShotsController {
    private final ApplicationContext applicationContext;
    private final ShotService shotService;
    private final JwtService jwtService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@CookieValue("accessToken") String token, @RequestBody @Validated ShotRequest request) {
        RequestProcessor processor = getRequestProcessor(request);
        Shot shot = processor.process(request);
        shotService.addShot(shot, jwtService.getUsername(token));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ShotResponse> getPoints(@CookieValue("accessToken") String token) {
        return shotService.getShots(jwtService.getUsername(token)).stream().map(ShotMapper::toResponse).toList();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void clear(@CookieValue("accessToken") String token) {
        shotService.clearShots(jwtService.getUsername(token));
    }

    private RequestProcessor getRequestProcessor(ShotRequest request) {
        Class<? extends RequestProcessor> processorClass = request.getWeapon().getProcessorClass();
        return applicationContext.getBean(processorClass);
    }
}