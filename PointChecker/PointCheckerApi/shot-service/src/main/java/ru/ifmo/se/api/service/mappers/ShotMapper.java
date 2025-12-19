package ru.ifmo.se.api.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;
import ru.ifmo.se.api.common.dto.shot.RevolverDetails;
import ru.ifmo.se.api.common.dto.shot.ShotDetails;
import ru.ifmo.se.api.common.dto.shot.ShotResponse;
import ru.ifmo.se.api.common.dto.shot.ShotgunDetails;
import ru.ifmo.se.api.service.entities.RevolverShotEntity;
import ru.ifmo.se.api.service.entities.ShotEntity;
import ru.ifmo.se.api.service.entities.ShotgunShotEntity;
import ru.ifmo.se.api.service.models.RevolverShot;
import ru.ifmo.se.api.service.models.Shot;
import ru.ifmo.se.api.service.models.ShotgunShot;

@Mapper(
        componentModel = "spring",
        uses = {BulletMapper.class}
)
public interface ShotMapper {
    @Mapping(target = "x", source = "x", qualifiedByName = "formatToPlainString")
    @Mapping(target = "y", source = "y", qualifiedByName = "formatToPlainString")
    @Mapping(target = "r", source = "r", qualifiedByName = "formatToPlainString")
    @Mapping(target = "details", source = "shot")
    ShotResponse toResponse(Shot shot);

    default ShotDetails mapDetails(Shot shot) {
        if (shot instanceof RevolverShot s) return toRevolverDetails(s);
        if (shot instanceof ShotgunShot s) return toShotgunDetails(s);
        return new ShotDetails("");
    }

    RevolverDetails toRevolverDetails(RevolverShot shot);
    ShotgunDetails toShotgunDetails(ShotgunShot shot);

    @SubclassMapping(source = RevolverShot.class, target = RevolverShotEntity.class)
    @SubclassMapping(source = ShotgunShot.class, target = ShotgunShotEntity.class)
    ShotEntity toEntity(Shot shot);

    RevolverShotEntity mapToRevolverEntity(RevolverShot shot);
    ShotgunShotEntity mapToShotgunEntity(ShotgunShot shot);

    @SubclassMapping(source = RevolverShotEntity.class, target = RevolverShot.class)
    @SubclassMapping(source = ShotgunShotEntity.class, target = ShotgunShot.class)
    Shot toModel(ShotEntity shotEntity);

    RevolverShot mapToRevolverModel(RevolverShotEntity entity);
    ShotgunShot mapToShotgunModel(ShotgunShotEntity entity);
}