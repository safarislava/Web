package ru.ifmo.se.api.common.dto.shot;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BulletDto {
    private String x;
    private String y;
    private Boolean hit;
}
