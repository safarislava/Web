package ru.ifmo.se.api.shotsmodule.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShotgunDetails extends ShotDetails {
    private final List<BulletDto> bullets;

    public ShotgunDetails(List<BulletDto> bullets) {
        super("Shotgun");
        this.bullets = bullets;
    }
}
