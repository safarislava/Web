package ru.ifmo.se.api.service.mappers;

import org.mapstruct.Mapper;
import ru.ifmo.se.api.common.dto.shot.WeaponDto;
import ru.ifmo.se.api.service.models.Weapon;

@Mapper(componentModel = "spring")
public interface WeaponMapper {
    Weapon toModel(WeaponDto weaponDto);
    WeaponDto toDto(Weapon weapon);
}
