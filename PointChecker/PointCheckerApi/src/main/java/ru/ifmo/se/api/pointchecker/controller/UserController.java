package ru.ifmo.se.api.pointchecker.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ru.ifmo.se.api.pointchecker.database.UserHibernateRepository;
import ru.ifmo.se.api.pointchecker.dto.UserDto;
import ru.ifmo.se.api.pointchecker.entity.User;
import ru.ifmo.se.api.pointchecker.utils.SHA256;

@Stateless
public class UserController {
    @EJB
    private UserHibernateRepository userRepository;

    public boolean login(UserDto userDto) {
        User user = userRepository.getUser(userDto.username);
        if (user == null) return false;

        String verifyingPassword = SHA256.getHash(userDto.password + user.getSalt());
        return user.getPassword().equals(verifyingPassword);
    }

    public void register(UserDto userDto) throws IllegalArgumentException {
        User user = userRepository.getUser(userDto.username);
        if (user != null)
            throw new IllegalArgumentException("Username already exists");

        userRepository.addUser(userDto.username, userDto.password);
    }
}
