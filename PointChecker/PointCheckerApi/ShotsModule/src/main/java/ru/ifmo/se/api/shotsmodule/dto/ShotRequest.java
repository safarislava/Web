package ru.ifmo.se.api.shotsmodule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ShotRequest {
    @NotNull
    private BigDecimal x;
    @NotNull
    private BigDecimal y;
    @NotNull
    @Positive
    private BigDecimal r;
    @NotNull
    private WeaponDto weapon;
}