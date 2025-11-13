package ru.ifmo.se.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Bullet {
    private Long id;
    public Long version;
    private BigDecimal x;
    private BigDecimal y;
    private Boolean hit;

    public Bullet(BigDecimal x, BigDecimal y, Boolean hit) {
        this.x = x;
        this.y = y;
        this.hit = hit;
    }
}
