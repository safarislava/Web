package ru.ifmo.se.api.service.components.processors;

import ru.ifmo.se.api.service.models.Shot;
import ru.ifmo.se.api.service.models.Weapon;

import java.math.BigDecimal;

public interface RequestProcessor {
    Shot process(BigDecimal x, BigDecimal y,  BigDecimal r);
    Weapon getWeaponType();
}
