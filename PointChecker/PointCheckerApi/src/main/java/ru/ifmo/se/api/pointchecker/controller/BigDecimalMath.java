package ru.ifmo.se.api.pointchecker.controller;

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalMath {
    private static final MathContext mathContext = MathContext.DECIMAL128;

    public static BigDecimal cos(BigDecimal x) {
        BigDecimal result = BigDecimal.ONE;
        BigDecimal term = BigDecimal.ONE;
        BigDecimal xSquared = x.pow(2, mathContext);
        for (int n = 1; n <= 20; n++) {
            term = term.multiply(xSquared.negate(), mathContext);
            term = term.divide(BigDecimal.valueOf((2 * n - 1) * (2 * n)), mathContext);

            result = result.add(term, mathContext);
        }
        return result;
    }

    public static BigDecimal sin(BigDecimal x) {
        BigDecimal result = x;
        BigDecimal term = x;
        BigDecimal xSquared = x.pow(2, mathContext);

        for (int n = 1; n <= 20; n++) {
            term = term.multiply(xSquared.negate(), mathContext);
            term = term.divide(BigDecimal.valueOf((2 * n) * (2 * n + 1)), mathContext);

            result = result.add(term, mathContext);
        }
        return result;
    }
}
