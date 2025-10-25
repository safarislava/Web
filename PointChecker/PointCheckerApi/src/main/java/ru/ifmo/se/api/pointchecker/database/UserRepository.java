package ru.ifmo.se.api.pointchecker.database;

import ru.ifmo.se.api.pointchecker.entity.User;

public interface UserRepository {
    void addUser(String username, String password);
    User getUser(String username);
}
