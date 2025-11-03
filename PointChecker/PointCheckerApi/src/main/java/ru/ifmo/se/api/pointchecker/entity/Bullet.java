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

    public Boolean getIsPointInArea() {
        return isPointInArea;
    }

    public void setIsPointInArea(Boolean isPointInArea) {
        this.isPointInArea = isPointInArea;
    }
}
