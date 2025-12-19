package ru.ifmo.se.api.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.service.entities.UserEntity;
import ru.ifmo.se.api.service.mappers.UserMapper;
import ru.ifmo.se.api.service.models.User;
import ru.ifmo.se.api.service.repositories.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public User getUser(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new IllegalArgumentException("User not found");
        return userMapper.toModel(user.get());
    }

    public User getUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new IllegalArgumentException("User not found");
        return userMapper.toModel(user.get());
    }

    public boolean login(String  username, String password) {
        User user = getUser(username);
        return passwordEncoder.matches(password, user.getPassword());
    }

    public User register(String  username, String password) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) throw new IllegalArgumentException("Username already exists");

        String hashedPassword = passwordEncoder.encode(password);
        return userMapper.toModel(userRepository.save(new UserEntity(username, hashedPassword)));
    }

    public User register(String  username) {
        return register(username, UUID.randomUUID().toString());
    }

    public void update(Long userId) {
        User user = getUser(userId);
        user.setLastUpdate(Instant.now());
        userRepository.save(userMapper.toEntity(user));
    }

    public boolean isUpdatedAfter(Instant time, Long  userId) {
        return getUser(userId).getLastUpdate().isAfter(time);
    }
}
