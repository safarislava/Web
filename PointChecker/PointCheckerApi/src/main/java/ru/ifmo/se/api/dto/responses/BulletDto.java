package ru.ifmo.se.api.dto.responses;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.entities.BulletEntity;
import ru.ifmo.se.api.models.Bullet;

@Getter
@Setter
public class BulletDto {
    private String x;
    private String y;
    private Boolean hit;

    public BulletDto() {}

    public BulletDto(Bullet bullet) {
        this.x = bullet.getX().stripTrailingZeros().toPlainString();
        this.y = bullet.getY().stripTrailingZeros().toPlainString();
        this.hit = bullet.getHit();
    }
}
