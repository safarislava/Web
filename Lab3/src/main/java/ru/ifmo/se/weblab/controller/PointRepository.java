package ru.ifmo.se.weblab.controller;

import ru.ifmo.se.weblab.dto.ShapablePointResponse;

import java.util.List;

public interface PointRepository extends AutoCloseable {
    void save(List<ShapablePointResponse> points);
    List<ShapablePointResponse> findAll();
}
