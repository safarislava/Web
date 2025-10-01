package ru.ifmo.se.webapp.controller;

import ru.ifmo.se.webapp.dto.PointRequest;

import java.math.BigDecimal;

public class ValidationController {
    public void validate(PointRequest pointRequest) throws IllegalArgumentException {
        if (pointRequest.x == null) {
            throw new IllegalArgumentException("X не указан");
        }
        if (pointRequest.y == null) {
            throw new IllegalArgumentException("Y не указан");
        }
        if (pointRequest.r == null) {
            throw new IllegalArgumentException("R не указан");
        }

        if (pointRequest.y.compareTo(BigDecimal.valueOf(-5)) < 0 || pointRequest.y.compareTo(BigDecimal.valueOf(3)) > 0) {
            throw new IllegalArgumentException("Y принимает недопустимые значения");
        }
    }
}
