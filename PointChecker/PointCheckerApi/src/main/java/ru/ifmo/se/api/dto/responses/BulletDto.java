package ru.ifmo.se.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.entities.BulletEntity;
import ru.ifmo.se.api.models.Bullet;

@Getter
@Setter
@AllArgsConstructor
public class BulletDto {
    private String x;
    private String y;
    private Boolean hit;
}
