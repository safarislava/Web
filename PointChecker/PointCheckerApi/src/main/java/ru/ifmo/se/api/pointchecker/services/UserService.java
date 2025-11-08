package ru.ifmo.se.api.pointchecker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.pointchecker.repositories.UserRepository;
import ru.ifmo.se.api.pointchecker.dto.UserDto;
import ru.ifmo.se.api.pointchecker.entities.User;
import ru.ifmo.se.api.pointchecker.utils.SHA256;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
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
