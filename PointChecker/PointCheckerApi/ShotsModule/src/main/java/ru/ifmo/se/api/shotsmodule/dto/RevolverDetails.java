package ru.ifmo.se.api.shotsmodule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevolverDetails extends ShotDetails {
    private final BulletDto bullet;

    public RevolverDetails(BulletDto bullet) {
        super("Revolver");
        this.bullet = bullet;
    }
}