package ru.ifmo.se.weblab.controller;

import ru.ifmo.se.weblab.dto.PointResponse;

import java.util.List;

public interface PointRepository extends AutoCloseable {
    void save(List<PointResponse> points);
    List<PointResponse> findAll();
}
