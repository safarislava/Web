package ru.ifmo.se.api.shotsmodule.dto;

import lombok.Getter;

@Getter
public class RevolverDetails extends ShotDetails {
    private final BulletDto bullet;

    public RevolverDetails(BulletDto bullet) {
        super("Revolver");
        this.bullet = bullet;
    }
}