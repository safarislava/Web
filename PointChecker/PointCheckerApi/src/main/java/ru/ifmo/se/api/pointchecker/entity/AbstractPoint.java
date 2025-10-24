package ru.ifmo.se.api.pointchecker.entity;

import java.math.BigDecimal;

public class AbstractPoint {
    public BigDecimal x;
    public BigDecimal y;

    public AbstractPoint(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }
}
