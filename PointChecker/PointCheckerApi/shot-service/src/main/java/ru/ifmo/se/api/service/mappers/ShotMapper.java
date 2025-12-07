package ru.ifmo.se.api.service.mappers;

import ru.ifmo.se.api.common.dto.shot.*;
import ru.ifmo.se.api.service.entities.*;
import ru.ifmo.se.api.service.models.*;

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

        private ShotResponse.ShotResponseBuilder getCommonBuilder(Shot shot) {
            return ShotResponse.builder()
                    .id(shot.getId())
                    .x(shot.getX().stripTrailingZeros().toPlainString())
                    .y(shot.getY().stripTrailingZeros().toPlainString())
                    .r(shot.getR().stripTrailingZeros().toPlainString())
                    .accuracy(shot.getAccuracy())
                    .deltaTime(shot.getDeltaTime())
                    .time(shot.getTime().toString());
        }

        @Override
        public ShotResponse visit(Shot shot) {
            return getCommonBuilder(shot).details(new ShotDetails("")).build();
        }

        @Override
        public ShotResponse visit(RevolverShot shot) {
            BulletDto bullet = BulletMapper.toDto(shot.getBullet());
            return getCommonBuilder(shot).details(new RevolverDetails(bullet)).build();
        }

        @Override
        public ShotResponse visit(ShotgunShot shot) {
            List<BulletDto> bullets = shot.getBullets().stream().map(BulletMapper::toDto).toList();
            return getCommonBuilder(shot).details(new ShotgunDetails(bullets)).build();
        }
    }

    private static class ToEntityVisitor implements ShotVisitor<ShotEntity> {
        private static final ToEntityVisitor INSTANCE = new ToEntityVisitor();

        private void fillCommonFields(Shot shot, ShotEntity.ShotEntityBuilder<?, ?> builder) {
            builder.id(shot.getId())
                    .version(shot.getVersion())
                    .x(shot.getX())
                    .y(shot.getY())
                    .r(shot.getR())
                    .userId(shot.getUserId())
                    .accuracy(shot.getAccuracy())
                    .deltaTime(shot.getDeltaTime())
                    .time(shot.getTime());
        }

        @Override
        public ShotEntity visit(Shot shot) {
            var builder = ShotEntity.builder();
            fillCommonFields(shot, builder);
            return builder.build();
        }

        @Override
        public ShotEntity visit(RevolverShot shot) {
            var builder = RevolverShotEntity.builder();
            fillCommonFields(shot, builder);
            BulletEntity bullet = BulletMapper.toEntity(shot.getBullet());
            return builder.bullet(bullet).build();
        }

        @Override
        public ShotEntity visit(ShotgunShot shot) {
            var builder = ShotgunShotEntity.builder();
            fillCommonFields(shot, builder);
            List<BulletEntity> bullets = shot.getBullets().stream().map(BulletMapper::toEntity).toList();
            return builder.bullets(bullets).build();
        }
    }

    private static class ToModelVisitor implements ShotEntityVisitor<Shot> {
        private static final ToModelVisitor INSTANCE = new ToModelVisitor();

        private void fillCommonFields(ShotEntity entity, Shot.ShotBuilder<?, ?> builder) {
            builder.id(entity.getId())
                    .version(entity.getVersion())
                    .x(entity.getX())
                    .y(entity.getY())
                    .r(entity.getR())
                    .userId(entity.getUserId())
                    .accuracy(entity.getAccuracy())
                    .deltaTime(entity.getDeltaTime())
                    .time(entity.getTime());
        }

        @Override
        public Shot visit(ShotEntity shotEntity) {
            var builder = Shot.builder();
            fillCommonFields(shotEntity, builder);
            return builder.build();
        }

        @Override
        public Shot visit(RevolverShotEntity shotEntity) {
            var builder = RevolverShot.builder();
            fillCommonFields(shotEntity, builder);
            return builder.bullet(BulletMapper.toModel(shotEntity.getBullet())).build();
        }

        @Override
        public Shot visit(ShotgunShotEntity shotEntity) {
            var builder = ShotgunShot.builder();
            fillCommonFields(shotEntity, builder);
            List<Bullet> bullets = shotEntity.getBullets().stream().map(BulletMapper::toModel).toList();
            return builder.bullets(bullets).build();
        }
    }
}
