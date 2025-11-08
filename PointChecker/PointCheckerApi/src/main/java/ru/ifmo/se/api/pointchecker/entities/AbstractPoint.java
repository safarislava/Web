package ru.ifmo.se.api.pointchecker.entities;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class AbstractPoint {
    public BigDecimal x;
    public BigDecimal y;
}
