package ru.ifmo.se.api.pointchecker.controller;

import jakarta.ejb.Stateless;

import java.math.BigDecimal;

@Stateless
public class CalculationBean {
    public boolean checkPointInArea(BigDecimal x, BigDecimal y, BigDecimal r) {
        return true;
    }
}
