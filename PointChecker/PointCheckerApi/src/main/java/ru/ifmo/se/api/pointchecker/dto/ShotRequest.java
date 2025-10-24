package ru.ifmo.se.api.pointchecker.dto;

import java.math.BigDecimal;

public class ShotRequest {
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

    public ShotRequest(String x, String y, String r, String weapon) {
        this(new BigDecimal(x), new BigDecimal(y), new BigDecimal(r), Weapon.valueOf(weapon));
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s",x.toPlainString(), y.toPlainString(), r.toPlainString());
    }
}