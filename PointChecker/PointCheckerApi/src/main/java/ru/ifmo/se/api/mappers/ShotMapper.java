package ru.ifmo.se.api.mappers;

import ru.ifmo.se.api.dto.responses.ShotDetails;
import ru.ifmo.se.api.dto.responses.ShotResponse;
import ru.ifmo.se.api.entities.*;
import ru.ifmo.se.api.models.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class ShotMapper {
    public static ShotResponse toResponse(Shot shot) {
        Long id = shot.getId();
        String x = shot.getX().stripTrailingZeros().toPlainString();
        String y = shot.getY().stripTrailingZeros().toPlainString();
        String r = shot.getR().stripTrailingZeros().toPlainString();
        Integer accuracy = shot.getAccuracy();
        Integer deltaTime = shot.getDeltaTime();
        String time = shot.getTime().toString();
        ShotDetails details = shot.getDetailsDto();
        return new ShotResponse(id, x, y, r, accuracy, deltaTime, time, details);
    }

    public static ShotEntity toEntity(Shot shot) {
        Long id = shot.getId();
        Long version = shot.getVersion();
        BigDecimal x = shot.getX();
        BigDecimal y = shot.getY();
        BigDecimal r = shot.getR();
        UserEntity user = UserMapper.toEntity(shot.getUser());
        Integer accuracy = shot.getAccuracy();
        Integer deltaTime = shot.getDeltaTime();
        Timestamp time = shot.getTime();

        if (shot instanceof RevolverShot) {
            BulletEntity bullet = BulletMapper.toEntity(((RevolverShot) shot).getBullet());
            return new RevolverShotEntity(id, version, x, y, r, user, accuracy, deltaTime, time, bullet);
        }
        if (shot instanceof ShotgunShot) {
            List<BulletEntity> bullets = ((ShotgunShot) shot).getBullets().stream().map(BulletMapper::toEntity).toList();
            return new ShotgunShotEntity(id, version, x, y, r, user, accuracy, deltaTime, time, bullets);
        }
        return new ShotEntity(id, version, x, y, r, user, accuracy, deltaTime, time);
    }

    public static Shot toModel(ShotEntity shotEntity) {
        Long id = shotEntity.getId();
        Long version = shotEntity.getVersion();
        BigDecimal x = shotEntity.getX();
        BigDecimal y = shotEntity.getY();
        BigDecimal r = shotEntity.getR();
        User user = UserMapper.toModel(shotEntity.getUser());
        Integer accuracy = shotEntity.getAccuracy();
        Integer deltaTime = shotEntity.getDeltaTime();
        Timestamp time = shotEntity.getTime();

        if  (shotEntity instanceof RevolverShotEntity) {
            Bullet bullet = BulletMapper.toModel(((RevolverShotEntity) shotEntity).getBullet());
            return new RevolverShot(id, version, x, y, r, user, accuracy, deltaTime, time, bullet);
        }
        if (shotEntity instanceof ShotgunShotEntity) {
            List<Bullet> bullets = ((ShotgunShotEntity) shotEntity).getBullets().stream().map(BulletMapper::toModel).toList();
            return new ShotgunShot(id, version, x, y, r, user, accuracy, deltaTime, time, bullets);
        }
        return new Shot(id, version, x, y, r, user, accuracy, deltaTime, time);
    }
}
