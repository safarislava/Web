package ru.ifmo.se.server.entity;

import java.math.BigDecimal;

public class Point {
    public BigDecimal x;
    public BigDecimal y;

    public Point(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public Point(BigDecimal x, double y) {
        this.x = x;
        this.y = BigDecimal.valueOf(y);
    }

    public Point(double x, BigDecimal y) {
        this.x = BigDecimal.valueOf(x);
        this.y = y;
    }

    public Point(double x, double y) {
        this.x = BigDecimal.valueOf(x);
        this.y = BigDecimal.valueOf(y);
    }
}
