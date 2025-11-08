package ru.ifmo.se.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.components.RequestProcessor;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.dto.responses.ShotResponse;
import ru.ifmo.se.api.entities.Shot;
import ru.ifmo.se.api.entities.User;
import ru.ifmo.se.api.repositories.ShotRepository;
import ru.ifmo.se.api.repositories.UserRepository;
import ru.ifmo.se.api.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShotService {
    private final ApplicationContext applicationContext;
    private final ShotRepository shotRepository;
    private final UserRepository userRepository;
    private final ValidatorService validatorService;

    public List<ShotResponse> getShotResponses(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new IllegalArgumentException("User not found");

        List<Shot> shots = shotRepository.findAllByUser(user.get());
        List<ShotResponse> shotResponses = new ArrayList<>();
        shots.forEach(shot -> shotResponses.add(new ShotResponse(shot)));
        return shotResponses;
    }

    public void addShot(ShotRequest request, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new IllegalArgumentException("User not found");

        validatorService.validate(request);

        RequestProcessor processor = getRequestProcessor(request);
        Shot shot = processor.process(request, user.get());

        shotRepository.save(shot);
    }

    public void clearShots(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new IllegalArgumentException("User not found");

        shotRepository.deleteAllByUser(user.get());
    }

    private RequestProcessor getRequestProcessor(ShotRequest request) {
        Class<? extends RequestProcessor> processorClass = request.weapon.getProcessorClass();
        return applicationContext.getBean(processorClass);
    }
}
