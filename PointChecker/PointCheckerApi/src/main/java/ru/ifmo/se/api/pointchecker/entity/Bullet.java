package ru.ifmo.se.api.pointchecker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "bullet")
public class Bullet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @Column(precision = 25, scale = 20)
    private BigDecimal x;
    @Column(precision = 25, scale = 20)
    private BigDecimal y;
    private Boolean isPointInArea;

    public Bullet() {}

    public Bullet(BigDecimal x, BigDecimal y, Boolean isPointInArea) {
        this.x = x;
        this.y = y;
        this.isPointInArea = isPointInArea;
    }
}
