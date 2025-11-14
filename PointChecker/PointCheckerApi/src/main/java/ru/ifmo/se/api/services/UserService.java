package ru.ifmo.se.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.entities.UserEntity;
import ru.ifmo.se.api.exceptions.BadRequestException;
import ru.ifmo.se.api.mappers.UserMapper;
import ru.ifmo.se.api.models.User;
import ru.ifmo.se.api.repositories.UserRepository;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new BadRequestException("User not found");
        return UserMapper.toModel(user.get());
    }

    public boolean login(String  username, String password) {
        User user = getUser(username);
        return passwordEncoder.matches(password, user.getPassword());
    }

    public void register(String  username, String password) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) throw new BadRequestException("Username already exists");

        String hashedPassword = passwordEncoder.encode(password);
        userRepository.save(new UserEntity(username, hashedPassword));
    }

    public void update(String username) {
        User user = getUser(username);
        user.setLastUpdate(Instant.now());
        userRepository.save(UserMapper.toEntity(user));
    }

    public boolean isUpdatedAfter(Instant time, String username) {
        return getUser(username).getLastUpdate().isAfter(time);
    }
}
