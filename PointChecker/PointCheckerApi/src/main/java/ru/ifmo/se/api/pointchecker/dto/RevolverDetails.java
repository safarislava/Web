package ru.ifmo.se.api.pointchecker.dto;

public class RevolverDetails extends BulletDetails {
    private BulletDto bullet;

    public RevolverDetails(BulletDto bullet) {
        super("Revolver");
        this.bullet = bullet;
    }

    public BulletDto getBullet() {
        return bullet;
    }
}