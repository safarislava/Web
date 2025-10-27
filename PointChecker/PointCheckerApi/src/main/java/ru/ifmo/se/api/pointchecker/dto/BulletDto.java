package ru.ifmo.se.api.pointchecker.dto;

import ru.ifmo.se.api.pointchecker.entity.Bullet;

import java.math.BigDecimal;

public class BulletDto {
    private Long id;
    private BigDecimal x;
    private BigDecimal y;
    private Boolean isPointInArea;

    public BulletDto() {}

    public BulletDto(Bullet bullet) {
        this.id = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.isPointInArea = bullet.getIsPointInArea();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
