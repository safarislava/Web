package ru.ifmo.se.api.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Shot {
    private Long id;
    private Long version;
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private Long userId;
    private Integer accuracy;
    private Integer deltaTime;
    private Timestamp time;
}
