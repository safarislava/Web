package ru.ifmo.se.api.pointchecker.dto;

import java.util.List;

public class ShotgunDetails {
    private List<BulletDto> bullets;

    public ShotgunDetails(List<BulletDto> bullets) {
        this.bullets = bullets;
    }

    public List<BulletDto> getBullets() {
        return bullets;
    }
}
