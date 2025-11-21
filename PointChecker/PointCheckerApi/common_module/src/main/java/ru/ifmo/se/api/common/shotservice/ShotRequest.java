package ru.ifmo.se.api.common.shotservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShotRequest {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private WeaponDto weapon;
}