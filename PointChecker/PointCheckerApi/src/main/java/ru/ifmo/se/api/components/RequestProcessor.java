package ru.ifmo.se.api.components;

import ru.ifmo.se.api.models.Shot;

import java.math.BigDecimal;

public interface RequestProcessor {
    Shot process(BigDecimal x, BigDecimal y,  BigDecimal r);
}
