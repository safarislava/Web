package ru.ifmo.se.api.service.mappers;

import ru.ifmo.se.api.common.dto.shot.BulletDto;
import ru.ifmo.se.api.service.entities.BulletEntity;
import ru.ifmo.se.api.service.models.Bullet;

public class BulletMapper {
    public static Bullet toModel(BulletEntity bulletEntity) {
        return Bullet.builder()
                .id(bulletEntity.getId())
                .version(bulletEntity.getVersion())
                .x(bulletEntity.getX())
                .y(bulletEntity.getY())
                .hit(bulletEntity.getHit())
                .build();
    }

    public static BulletDto toDto(Bullet bullet) {
        return BulletDto.builder()
                .x(bullet.getX().stripTrailingZeros().toPlainString())
                .y(bullet.getY().stripTrailingZeros().toPlainString())
                .hit(bullet.getHit())
                .build();
    }

    public static BulletEntity toEntity(Bullet bullet) {
        return BulletEntity.builder()
                .id(bullet.getId())
                .version(bullet.getVersion())
                .x(bullet.getX())
                .y(bullet.getY())
                .hit(bullet.getHit())
                .build();
    }
}
