package ru.ifmo.se.weblab.dto;

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

    public PointRequest(String x, String y, String r) {
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        this.r = new BigDecimal(r);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s",x.toPlainString(), y.toPlainString(), r.toPlainString());
    }
}