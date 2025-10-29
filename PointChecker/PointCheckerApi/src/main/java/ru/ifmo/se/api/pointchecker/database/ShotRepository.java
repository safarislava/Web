package ru.ifmo.se.api.pointchecker.database;

import ru.ifmo.se.api.pointchecker.entity.Shot;
import ru.ifmo.se.api.pointchecker.entity.User;

import java.util.List;

public interface ShotRepository {
    List<Shot> findAll(User user);
    void save(List<Shot> points);
    void clear(User user);
}
