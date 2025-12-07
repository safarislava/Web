package ru.ifmo.se.api.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Shot {
    private Long id;
    private Long version;
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private Long userId;
    private Integer accuracy;
    private Integer deltaTime;
    private Timestamp time;

    public Shot() {}

    public Shot(BigDecimal x, BigDecimal y, BigDecimal r, Integer accuracy, Integer deltaTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.accuracy = accuracy;
        this.deltaTime = deltaTime;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public <R> R accept(ShotVisitor<R> visitor){
        return visitor.visit(this);
    }
}
