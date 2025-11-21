package ru.ifmo.se.api.service.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class RevolverShot extends Shot {
    private Bullet bullet;

    public RevolverShot() {}

    public RevolverShot(BigDecimal x, BigDecimal y, BigDecimal r, Integer deltaTime, Bullet bullet) {
        super(x, y, r, bullet.getHit() ? 100 : 0,  deltaTime);
        this.bullet = bullet;
    }

    public RevolverShot(Long id, Long version, BigDecimal x, BigDecimal y, BigDecimal r, Long userId,
                        Integer accuracy, Integer deltaTime, Timestamp time, Bullet bullet) {
        super(id, version, x, y, r, userId, accuracy, deltaTime, time);
        this.bullet = bullet;
    }

    public <R> R accept(ShotVisitor<R> visitor){
        return visitor.visit(this);
    }
}
