package ru.ifmo.se.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.entities.UserEntity;
import ru.ifmo.se.api.exceptions.BadRequestException;
import ru.ifmo.se.api.repositories.UserRepository;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean login(String  username, String password) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) throw new BadRequestException("Username does not exist");
        return userEntity.map(u -> passwordEncoder.matches(password, u.getPassword())).orElse(false);
    }

    public void register(String  username, String password) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) throw new BadRequestException("Username already exists");
        String hashedPassword = passwordEncoder.encode(password);
        userRepository.save(new UserEntity(username, hashedPassword));
    }

    public void setLastUpdate(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) throw new BadRequestException("Username does not exist");
        userEntity.get().setLastUpdate(Instant.now());
        userRepository.save(userEntity.get());
    }

    public Instant getLastUpdate(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) throw new BadRequestException("Username does not exist");
        return userEntity.get().getLastUpdate();
    }
}
