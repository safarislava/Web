package ru.ifmo.se.api.pointchecker.dto;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.pointchecker.entity.Bullet;

@Getter
@Setter
public class BulletDto {
    private Long id;
    private String x;
    private String y;
    private Boolean isPointInArea;

    public BulletDto() {}

    public BulletDto(Bullet bullet) {
        this.id = bullet.getId();
        this.x = bullet.getX().stripTrailingZeros().toPlainString();
        this.y = bullet.getY().stripTrailingZeros().toPlainString();
        this.isPointInArea = bullet.getIsPointInArea();
    }
}
