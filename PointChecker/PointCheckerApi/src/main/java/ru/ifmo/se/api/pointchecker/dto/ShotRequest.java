package ru.ifmo.se.api.pointchecker.dto;

import java.math.BigDecimal;

public class ShotRequest {
    public String username;
    public BigDecimal x;
    public BigDecimal y;
    public BigDecimal r;
    public Weapon weapon;

    public ShotRequest() {}

    public ShotRequest(BigDecimal x, BigDecimal y, BigDecimal r, Weapon weapon) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.weapon = weapon;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s",x.toPlainString(), y.toPlainString(), r.toPlainString());
    }
}