package ru.ifmo.se.api.pointchecker.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ShotgunDetails extends ShotDetails {
    private final List<BulletDto> bullets;

    public ShotgunDetails(List<BulletDto> bullets) {
        super("Shotgun");
        this.bullets = bullets;
    }


}
