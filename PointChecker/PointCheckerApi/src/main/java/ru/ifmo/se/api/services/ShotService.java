package ru.ifmo.se.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.entities.ShotEntity;
import ru.ifmo.se.api.entities.UserEntity;
import ru.ifmo.se.api.mappers.ShotMapper;
import ru.ifmo.se.api.mappers.UserMapper;
import ru.ifmo.se.api.models.Shot;
import ru.ifmo.se.api.models.User;
import ru.ifmo.se.api.repositories.ShotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShotService {
    private final ShotRepository shotRepository;
    private final UserService userService;

    public List<Shot> getShots(String username) {
        User user = userService.getUser(username);
        return shotRepository.findAllByUser(UserMapper.toEntity(user)).stream().map(ShotMapper::toModel).toList();
    }

    public void addShot(Shot shot, String username) {
        User user = userService.getUser(username);
        shot.setUser(user);
        shotRepository.save(ShotMapper.toEntity(shot));
    }

    public void clearShots(String username) {
        User user = userService.getUser(username);
        shotRepository.deleteAllByUser(UserMapper.toEntity(user));
    }
}
