package ru.ifmo.se.api.pointchecker.dto;

import ru.ifmo.se.api.pointchecker.entity.Bullet;

public class BulletDto {
    private Long id;
    private String x;
    private String y;
    private Boolean isPointInArea;

    public BulletDto() {}

    public BulletDto(Bullet bullet) {
        this.id = bullet.getId();
        this.x = bullet.getX().stripTrailingZeros().toPlainString();
        this.y = bullet.getY().stripTrailingZeros().toPlainString();
        this.isPointInArea = bullet.getIsPointInArea();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }
    public void setY(String y) {
        this.y = y;
    }

    public Boolean getIsPointInArea() {
        return isPointInArea;
    }
    public void setIsPointInArea(Boolean isPointInArea) {
        this.isPointInArea = isPointInArea;
    }
}
