package ru.ifmo.se.api.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.ifmo.se.api.common.dto.shot.BulletDto;
import ru.ifmo.se.api.service.entities.BulletEntity;
import ru.ifmo.se.api.service.models.Bullet;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface BulletMapper {
    Bullet toModel(BulletEntity bulletEntity);
    BulletEntity toEntity(Bullet bullet);

    @Mapping(target = "x", source = "x", qualifiedByName = "formatToPlainString")
    @Mapping(target = "y", source = "y", qualifiedByName = "formatToPlainString")
    BulletDto toDto(Bullet bullet);

    @Named("formatToPlainString")
    default String formatToPlainString(BigDecimal value) {
        if (value == null) return null;
        return value.stripTrailingZeros().toPlainString();
    }
}
