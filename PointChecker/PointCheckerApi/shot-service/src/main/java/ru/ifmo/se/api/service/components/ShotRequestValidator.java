package ru.ifmo.se.api.service.components;

import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.dto.shot.ShotRequest;

import java.math.BigDecimal;

@Component
public class ShotRequestValidator {
    public void validate(ShotRequest shotRequest) {
        if (shotRequest == null) throw new IllegalArgumentException("ShotRequest is null");
        if (shotRequest.getX() == null) throw new IllegalArgumentException("X is null");
        if (shotRequest.getY() == null) throw new IllegalArgumentException("Y is null");
        if (shotRequest.getR() == null) throw new IllegalArgumentException("R is null");
        if (shotRequest.getR().compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("R is negative");
    }
}
