package ru.ifmo.se.api.pointchecker.dto;

import java.util.List;

public class ShotgunDetails extends BulletDetails {
    private List<BulletDto> bullets;

    public ShotgunDetails(List<BulletDto> bullets) {
        super("Shotgun");
        this.bullets = bullets;
    }

    public List<BulletDto> getBullets() {
        return bullets;
    }
}
