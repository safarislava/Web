package ru.ifmo.se.api.pointchecker.dto;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class ShotRequest {
    public BigDecimal x;
    public BigDecimal y;
    public BigDecimal r;
    public Weapon weapon;

    public ShotRequest() {}
}