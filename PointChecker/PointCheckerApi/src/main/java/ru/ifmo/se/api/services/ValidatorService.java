package ru.ifmo.se.api.services;

import org.springframework.stereotype.Service;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.exceptions.BadRequestException;

import java.math.BigDecimal;

@Service
public class ValidatorService {
    public void validate(ShotRequest shotRequest) throws IllegalArgumentException {
        if (shotRequest.x == null) {
            throw new BadRequestException("X is null");
        }
        if (shotRequest.y == null) {
            throw new BadRequestException("Y is null");
        }
        if (shotRequest.r == null) {
            throw new BadRequestException("R is null");
        }
        if (shotRequest.r.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("R is below 0");
        }
    }
}
