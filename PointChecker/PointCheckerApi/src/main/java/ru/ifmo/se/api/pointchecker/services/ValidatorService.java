package ru.ifmo.se.api.pointchecker.services;

import org.springframework.stereotype.Service;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;

import java.math.BigDecimal;

@Service
public class ValidatorService {
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
        if (shotRequest.r.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("R неположительный");
        }
    }
}
