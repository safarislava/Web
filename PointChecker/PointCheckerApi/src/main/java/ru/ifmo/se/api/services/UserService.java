package ru.ifmo.se.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.exceptions.BadRequestException;
import ru.ifmo.se.api.repositories.UserRepository;
import ru.ifmo.se.api.dto.requests.UserDto;
import ru.ifmo.se.api.entities.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean login(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) throw new BadRequestException("Username does not exist");
        return userOptional.map(user -> passwordEncoder.matches(username, user.getPassword())).orElse(false);
    }

    public void register(UserDto userDto) {
        Optional<User> user = userRepository.findByUsername(userDto.getUsername());
        if (user.isPresent()) throw new BadRequestException("Username already exists");
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        userRepository.save(new User(userDto.getUsername(), hashedPassword));
    }
}
