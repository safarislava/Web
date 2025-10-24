package ru.ifmo.se.api.pointchecker.database;

import ru.ifmo.se.api.pointchecker.entity.Shot;

import java.util.List;

public interface ShotRepository {
    List<Shot> findAll();
    void save(List<Shot> points);
}
