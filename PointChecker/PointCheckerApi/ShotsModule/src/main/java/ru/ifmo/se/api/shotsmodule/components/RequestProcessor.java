package ru.ifmo.se.api.shotsmodule.components;

import ru.ifmo.se.api.shotsmodule.models.Shot;

import java.math.BigDecimal;

public interface RequestProcessor {
    Shot process(BigDecimal x, BigDecimal y,  BigDecimal r);
}
