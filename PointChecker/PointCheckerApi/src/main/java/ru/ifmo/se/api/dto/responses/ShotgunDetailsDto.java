package ru.ifmo.se.api.dto.responses;

import lombok.Getter;

import java.util.List;

@Getter
public class ShotgunDetailsDto extends ShotDetailsDto {
    private final List<BulletDto> bullets;

    public ShotgunDetailsDto(List<BulletDto> bullets) {
        super("Shotgun");
        this.bullets = bullets;
    }
}
