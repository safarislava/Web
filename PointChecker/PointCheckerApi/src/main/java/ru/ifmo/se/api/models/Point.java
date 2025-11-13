package ru.ifmo.se.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Point {
    private BigDecimal x;
    private BigDecimal y;
}
