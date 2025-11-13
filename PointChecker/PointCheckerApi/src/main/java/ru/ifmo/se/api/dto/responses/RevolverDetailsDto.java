package ru.ifmo.se.api.dto.responses;

import lombok.Getter;

@Getter
public class RevolverDetailsDto extends ShotDetailsDto {
    private final BulletDto bullet;

    public RevolverDetailsDto(BulletDto bullet) {
        super("Revolver");
        this.bullet = bullet;
    }
}