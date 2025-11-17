package ru.ifmo.se.api.shotsmodule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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