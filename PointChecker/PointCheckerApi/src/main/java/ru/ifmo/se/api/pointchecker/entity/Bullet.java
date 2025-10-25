package ru.ifmo.se.api.pointchecker.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "bullet")
public class Bullet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private Boolean isPointInArea;

    public Bullet() {}

    public Bullet(BigDecimal x, BigDecimal y, BigDecimal r, Boolean isPointInArea) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isPointInArea = isPointInArea;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }

    public Boolean getPointInArea() {
        return isPointInArea;
    }

    public void setIsPointInArea(Boolean isPointInArea) {
        this.isPointInArea = isPointInArea;
    }
}
