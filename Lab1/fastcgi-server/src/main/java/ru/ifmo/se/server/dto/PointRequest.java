package ru.ifmo.se.server.dto;

import java.math.BigDecimal;

public class PointRequest {
    public BigDecimal x;
    public BigDecimal y;
    public BigDecimal r;

    public PointRequest(BigDecimal x, BigDecimal y, BigDecimal r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s",x.toPlainString(), y.toPlainString(), r.toPlainString());
    }
}