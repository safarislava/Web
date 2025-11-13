package ru.ifmo.se.api.models;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.entities.BulletEntity;

import java.math.BigDecimal;

@Getter
@Setter
public class Bullet {
    private Long id;
    private BigDecimal x;
    private BigDecimal y;
    private Boolean hit;

    public Bullet(BulletEntity entity) {
        id = entity.getId();
        x = entity.getX();
        y = entity.getY();
        hit = entity.getHit();
    }

    public Bullet(BigDecimal x, BigDecimal y, Boolean hit) {
        this.x = x;
        this.y = y;
        this.hit = hit;
    }
}
