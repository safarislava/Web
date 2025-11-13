package ru.ifmo.se.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ShotResponse {
    private Long id;
    private String x;
    private String y;
    private String r;
    private Integer accuracy;
    private Integer deltaTime;
    private String time;
    private ShotDetails details;
}
