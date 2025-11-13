package ru.ifmo.se.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.components.RequestProcessor;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.entities.ShotEntity;
import ru.ifmo.se.api.entities.UserEntity;
import ru.ifmo.se.api.exceptions.BadRequestException;
import ru.ifmo.se.api.models.Shot;
import ru.ifmo.se.api.models.User;
import ru.ifmo.se.api.repositories.ShotRepository;
import ru.ifmo.se.api.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShotService {
    private final ApplicationContext applicationContext;
    private final ShotRepository shotRepository;
    private final UserRepository userRepository;

    private User getUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new BadRequestException("User not found");
        return new User(user.get());
    }

    public List<Shot> getShots(String username) {
        User user = getUser(username);
        return shotRepository.findAllByUser(new UserEntity(user)).stream().map(ShotEntity::toModel).toList();
    }

    public void addShot(ShotRequest request, String username) {
        User user = getUser(username);
        RequestProcessor processor = getRequestProcessor(request);
        Shot shot = processor.process(request, user);
        shotRepository.save(shot.toEntity());
    }

    public void clearShots(String username) {
        User user = getUser(username);
        shotRepository.deleteAllByUser(new UserEntity(user));
    }

    private RequestProcessor getRequestProcessor(ShotRequest request) {
        Class<? extends RequestProcessor> processorClass = request.getWeapon().getProcessorClass();
        return applicationContext.getBean(processorClass);
    }
}
