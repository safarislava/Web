package ru.ifmo.se.api.pointchecker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.pointchecker.database.UserRepository;
import ru.ifmo.se.api.pointchecker.dto.UserDto;
import ru.ifmo.se.api.pointchecker.entity.User;
import ru.ifmo.se.api.pointchecker.utils.SHA256;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserBean {
    private final UserRepository userRepository;

    public boolean login(UserDto userDto) {
        Optional<User> user = userRepository.findByUsername(userDto.username);
        if (user.isEmpty()) return false;

        String verifyingPassword = SHA256.getHash(userDto.password + user.get().getSalt());
        return user.get().getPassword().equals(verifyingPassword);
    }

    public void register(UserDto userDto) throws IllegalArgumentException {
        Optional<User> user = userRepository.findByUsername(userDto.username);
        if (user.isPresent()) throw new IllegalArgumentException("Username already exists");

        userRepository.save(new User(userDto.username, userDto.password));
    }
}
