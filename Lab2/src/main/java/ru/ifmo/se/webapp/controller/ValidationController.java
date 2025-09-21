package ru.ifmo.se.webapp.controller;

import ru.ifmo.se.webapp.dto.PointRequest;

import java.math.BigDecimal;

public class ValidationController {
    private final BigDecimal[] xValidValues = new BigDecimal[]{
            new BigDecimal("-2"),
            new BigDecimal("-1.5"),
            new BigDecimal("-1"),
            new BigDecimal("-0.5"),
            new BigDecimal("0"),
            new BigDecimal("0.5"),
            new BigDecimal("1"),
            new BigDecimal("1.5"),
            new BigDecimal("2"),
    };

    private final BigDecimal[] rValidValues = new BigDecimal[]{
            new BigDecimal("1"),
            new BigDecimal("1.5"),
            new BigDecimal("2"),
            new BigDecimal("2.5"),
            new BigDecimal("3"),
    };

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

        boolean isXValid = false;
        for (BigDecimal x : xValidValues) {
            if (pointRequest.x.compareTo(x) == 0) {
                isXValid = true;
                break;
            }
        }
        if (!isXValid) {
            throw new IllegalArgumentException("X принимает недопустимые значения");
        }

        if (pointRequest.y.compareTo(BigDecimal.valueOf(-5)) < 0 || pointRequest.y.compareTo(BigDecimal.valueOf(3)) > 0) {
            throw new IllegalArgumentException("Y принимает недопустимые значения");
        }

        boolean isRValid = false;
        for (BigDecimal r : rValidValues) {
            if (pointRequest.r.compareTo(r) == 0) {
                isRValid = true;
                break;
            }
        }
        if (!isRValid) {
            throw new IllegalArgumentException("R принимает недопустимые значения");
        }
    }
}
