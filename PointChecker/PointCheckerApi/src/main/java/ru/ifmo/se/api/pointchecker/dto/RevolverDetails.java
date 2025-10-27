package ru.ifmo.se.api.pointchecker.dto;

public class RevolverDetails {
    private BulletDto bullet;

    public RevolverDetails(BulletDto bullet) {
        this.bullet = bullet;
    }

    public BulletDto getBullet() {
        return bullet;
    }
}