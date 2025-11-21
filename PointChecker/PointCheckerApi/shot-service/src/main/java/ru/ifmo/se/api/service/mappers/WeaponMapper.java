package ru.ifmo.se.api.service.mappers;

import ru.ifmo.se.api.common.shotservice.WeaponDto;
import ru.ifmo.se.api.service.models.Weapon;

public class WeaponMapper {
    public static Weapon toModel(WeaponDto weaponDto) {
        return Weapon.valueOf(weaponDto.name());
    }
}
