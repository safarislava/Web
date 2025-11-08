package ru.ifmo.se.api.pointchecker.dto.requests;

import lombok.AllArgsConstructor;
import ru.ifmo.se.api.pointchecker.entities.Weapon;

import java.math.BigDecimal;

@AllArgsConstructor
public class ShotRequest {
    public BigDecimal x;
    public BigDecimal y;
    public BigDecimal r;
    public Weapon weapon;

    public ShotRequest() {}
}