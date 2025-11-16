package ru.ifmo.se.api.coremodule.dto.shotsmodule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ShotRequest {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private WeaponDto weapon;
}