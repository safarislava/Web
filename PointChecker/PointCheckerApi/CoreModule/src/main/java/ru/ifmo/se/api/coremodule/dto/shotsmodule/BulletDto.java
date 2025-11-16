package ru.ifmo.se.api.coremodule.dto.shotsmodule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BulletDto {
    private String x;
    private String y;
    private Boolean hit;
}
