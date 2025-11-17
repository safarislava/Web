package ru.ifmo.se.api.shotsmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BulletDto {
    private String x;
    private String y;
    private Boolean hit;
}
