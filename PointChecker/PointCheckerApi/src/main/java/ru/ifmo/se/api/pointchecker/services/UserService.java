package ru.ifmo.se.api.pointchecker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.pointchecker.repositories.UserRepository;
import ru.ifmo.se.api.pointchecker.dto.UserDto;
import ru.ifmo.se.api.pointchecker.entities.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean login(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByUsername(userDto.username);
        return userOptional.map(user -> passwordEncoder.matches(userDto.password, user.getPassword())).orElse(false);
    }

    public void register(UserDto userDto) throws IllegalArgumentException {
        Optional<User> user = userRepository.findByUsername(userDto.username);
        if (user.isPresent()) throw new IllegalArgumentException("Username already exists");

        String hashedPassword = passwordEncoder.encode(userDto.password);
        userRepository.save(new User(userDto.username, hashedPassword));
    }
}
