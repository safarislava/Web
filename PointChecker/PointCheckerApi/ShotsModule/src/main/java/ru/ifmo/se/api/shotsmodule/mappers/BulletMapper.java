package ru.ifmo.se.api.shotsmodule.mappers;

import ru.ifmo.se.api.shotsmodule.dto.BulletDto;
import ru.ifmo.se.api.shotsmodule.entities.BulletEntity;
import ru.ifmo.se.api.shotsmodule.models.Bullet;

import java.math.BigDecimal;

public class BulletMapper {
    public static Bullet toModel(BulletEntity bulletEntity) {
        Long id = bulletEntity.getId();
        Long version = bulletEntity.getVersion();
        BigDecimal x = bulletEntity.getX();
        BigDecimal y = bulletEntity.getY();
        Boolean hit = bulletEntity.getHit();
        return new Bullet(id, version, x, y, hit);
    }

    public static BulletDto toDto(Bullet bullet) {
        String x = bullet.getX().stripTrailingZeros().toPlainString();
        String y = bullet.getY().stripTrailingZeros().toPlainString();
        Boolean hit = bullet.getHit();
        return new BulletDto(x, y, hit);
    }

    public static BulletEntity toEntity(Bullet bullet) {
        Long id = bullet.getId();
        Long version = bullet.getVersion();
        BigDecimal x = bullet.getX();
        BigDecimal y = bullet.getY();
        Boolean hit = bullet.getHit();
        return new BulletEntity(id, version, x, y, hit);
    }
}
