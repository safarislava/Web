package ru.ifmo.se.weblab.controller;

import ru.ifmo.se.weblab.entity.Point;

import java.util.List;

public interface PointRepository extends AutoCloseable {
    void save(List<Point> points);
    List<Point> findAll();
}
