package ru.ifmo.se.api.mappers;

import ru.ifmo.se.api.dto.responses.*;
import ru.ifmo.se.api.entities.*;
import ru.ifmo.se.api.models.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class ShotMapper {
    public static ShotResponse toResponse(Shot shot) {
        return shot.accept(ToResponseVisitor.INSTANCE);
    }

    public static ShotEntity toEntity(Shot shot) {
        return shot.accept(ToEntityVisitor.INSTANCE);
    }

    public static Shot toModel(ShotEntity shotEntity) {
        return shotEntity.accept(ToModelVisitor.INSTANCE);
    }

    private static class ToResponseVisitor implements ShotVisitor<ShotResponse> {
        private static final ToResponseVisitor INSTANCE = new ToResponseVisitor();

        private ShotResponse toBaseResponse(Shot shot) {
            String x = shot.getX().stripTrailingZeros().toPlainString();
            String y = shot.getY().stripTrailingZeros().toPlainString();
            String r = shot.getR().stripTrailingZeros().toPlainString();
            String time = shot.getTime().toString();
            return new ShotResponse(shot.getId(), x, y, r, shot.getAccuracy(), shot.getDeltaTime(), time, null);
        }

        @Override
        public ShotResponse visit(Shot shot) {
            ShotResponse response = toBaseResponse(shot);
            response.setDetails(new ShotDetails(""));
            return response;
        }

        @Override
        public ShotResponse visit(RevolverShot shot) {
            ShotResponse response = toBaseResponse(shot);
            BulletDto bullet = BulletMapper.toDto(shot.getBullet());
            response.setDetails(new RevolverDetails(bullet));
            return response;
        }

        @Override
        public ShotResponse visit(ShotgunShot shot) {
            ShotResponse response = toBaseResponse(shot);
            List<BulletDto> bullets = shot.getBullets().stream().map(BulletMapper::toDto).toList();
            response.setDetails(new ShotgunDetails(bullets));
            return response;
        }
    }

    private static class ToEntityVisitor implements ShotVisitor<ShotEntity> {
        private static final ToEntityVisitor INSTANCE = new ToEntityVisitor();

        @Override
        public ShotEntity visit(Shot shot) {
            return new ShotEntity(
                    shot.getId(), shot.getVersion(), shot.getX(), shot.getY(), shot.getR(),
                    UserMapper.toEntity(shot.getUser()), shot.getAccuracy(), shot.getDeltaTime(), shot.getTime()
            );
        }

        @Override
        public ShotEntity visit(RevolverShot shot) {
            BulletEntity bullet = BulletMapper.toEntity(shot.getBullet());
            return new RevolverShotEntity(
                    shot.getId(), shot.getVersion(), shot.getX(), shot.getY(), shot.getR(),
                    UserMapper.toEntity(shot.getUser()), shot.getAccuracy(), shot.getDeltaTime(), shot.getTime(),
                    bullet
            );
        }

        @Override
        public ShotEntity visit(ShotgunShot shot) {
            List<BulletEntity> bullets = shot.getBullets().stream().map(BulletMapper::toEntity).toList();
            return new ShotgunShotEntity(
                    shot.getId(), shot.getVersion(), shot.getX(), shot.getY(), shot.getR(),
                    UserMapper.toEntity(shot.getUser()), shot.getAccuracy(), shot.getDeltaTime(), shot.getTime(),
                    bullets
            );
        }
    }

    private static class ToModelVisitor implements ShotEntityVisitor<Shot> {
        private static final ToModelVisitor INSTANCE = new ToModelVisitor();

        @Override
        public Shot visit(ShotEntity shotEntity) {
            Long id = shotEntity.getId();
            Long version = shotEntity.getVersion();
            BigDecimal x = shotEntity.getX();
            BigDecimal y = shotEntity.getY();
            BigDecimal r = shotEntity.getR();
            User user = UserMapper.toModel(shotEntity.getUser());
            Integer accuracy = shotEntity.getAccuracy();
            Integer deltaTime = shotEntity.getDeltaTime();
            Timestamp time = shotEntity.getTime();
            return new Shot(id, version, x, y, r, user, accuracy, deltaTime, time);
        }

        @Override
        public Shot visit(RevolverShotEntity shotEntity) {
            Long id = shotEntity.getId();
            Long version = shotEntity.getVersion();
            BigDecimal x = shotEntity.getX();
            BigDecimal y = shotEntity.getY();
            BigDecimal r = shotEntity.getR();
            User user = UserMapper.toModel(shotEntity.getUser());
            Integer accuracy = shotEntity.getAccuracy();
            Integer deltaTime = shotEntity.getDeltaTime();
            Timestamp time = shotEntity.getTime();
            Bullet bullet = BulletMapper.toModel(shotEntity.getBullet());
            return new RevolverShot(id, version, x, y, r, user, accuracy, deltaTime, time, bullet);
        }

        @Override
        public Shot visit(ShotgunShotEntity shotEntity) {
            Long id = shotEntity.getId();
            Long version = shotEntity.getVersion();
            BigDecimal x = shotEntity.getX();
            BigDecimal y = shotEntity.getY();
            BigDecimal r = shotEntity.getR();
            User user = UserMapper.toModel(shotEntity.getUser());
            Integer accuracy = shotEntity.getAccuracy();
            Integer deltaTime = shotEntity.getDeltaTime();
            Timestamp time = shotEntity.getTime();
            List<Bullet> bullets = shotEntity.getBullets().stream().map(BulletMapper::toModel).toList();
            return new ShotgunShot(id, version, x, y, r, user, accuracy, deltaTime, time, bullets);
        }
    }
}
