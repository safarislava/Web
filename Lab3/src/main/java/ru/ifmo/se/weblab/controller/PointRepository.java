package ru.ifmo.se.weblab.controller;

import ru.ifmo.se.weblab.dto.PointResponse;

import java.util.List;

public interface PointRepository {
    // TODO ORACLE DB
    void save(List<PointResponse> points);
    List<PointResponse> findAll();
    void close();
}
