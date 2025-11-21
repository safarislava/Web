package ru.ifmo.se.api.service.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class ShotgunShot extends Shot {
    private List<Bullet> bullets;

    public ShotgunShot(Long id, Long version, BigDecimal x, BigDecimal y, BigDecimal r, Long userId,
                       Integer accuracy, Integer deltaTime, Timestamp time, List<Bullet> bullets) {
        super(id, version, x, y, r, userId, accuracy, deltaTime, time);
        this.bullets = bullets;
    }

    public ShotgunShot(BigDecimal x, BigDecimal y, BigDecimal r, Integer deltaTime, List<Bullet> bullets) {
        super(x, y, r, (int) bullets.stream().filter(Bullet::getHit).count() * 100 / bullets.size(), deltaTime);
        this.bullets = bullets;
    }

    public <R> R accept(ShotVisitor<R> visitor){
        return visitor.visit(this);
    }
}
