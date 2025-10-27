package ru.ifmo.se.api.pointchecker.controller;

import jakarta.ejb.Stateless;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;

import java.math.BigDecimal;

@Stateless
public class ValidatorBean {
    public void validate(ShotRequest shotRequest) throws IllegalArgumentException {
        if (shotRequest.x == null) {
            throw new IllegalArgumentException("X не указан");
        }
        if (shotRequest.y == null) {
            throw new IllegalArgumentException("Y не указан");
        }
        if (shotRequest.r == null) {
            throw new IllegalArgumentException("R не указан");
        }
    }
}
