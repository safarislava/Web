package ru.ifmo.se.api.models;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.dto.responses.BulletDto;
import ru.ifmo.se.api.dto.responses.RevolverDetailsDto;
import ru.ifmo.se.api.dto.responses.ShotDetailsDto;
import ru.ifmo.se.api.entities.RevolverShotEntity;
import ru.ifmo.se.api.entities.ShotEntity;

import java.math.BigDecimal;

@Getter
@Setter
public class RevolverShot extends Shot {
    private Bullet bullet;

    public RevolverShot(RevolverShotEntity entity) {
        super(entity);
        bullet = new Bullet(entity.getBullet());
    }

    public RevolverShot(BigDecimal x, BigDecimal y, BigDecimal r, User user, Integer deltaTime, Bullet bullet) {
        super(x, y, r, user, bullet.getHit() ? 100 : 0,  deltaTime);
        this.bullet = bullet;
    }

    @Override
    public ShotEntity toEntity() {
        return new RevolverShotEntity(this);
    }

    @Override
    public ShotDetailsDto getDetailsDto() {
        BulletDto bullet = new BulletDto(this.bullet);
        return new RevolverDetailsDto(bullet);
    }
}
