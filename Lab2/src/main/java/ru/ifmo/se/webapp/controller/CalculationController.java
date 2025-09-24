package ru.ifmo.se.webapp.controller;

import ru.ifmo.se.webapp.entity.Point;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class CalculationController {
    private final MathContext mathContext = MathContext.DECIMAL128;

    private final BigDecimal TWO = new BigDecimal("2");
    private final BigDecimal HUNDRED = new BigDecimal("100");
    private final BigDecimal HUNDRED_FIFTY = new BigDecimal("150");

    public BigDecimal cos(BigDecimal x) {
        BigDecimal result = BigDecimal.ONE;
        BigDecimal term = BigDecimal.ONE;
        BigDecimal xSquared = x.pow(2, mathContext);
        for (int n = 1; n <= 100; n++) {
            term = term.multiply(xSquared.negate(), mathContext);
            term = term.divide(BigDecimal.valueOf((2 * n - 1) * (2 * n)), mathContext);

            result = result.add(term, mathContext);
        }
        return result;
    }

    public BigDecimal sin(BigDecimal x) {
        BigDecimal result = x;
        BigDecimal term = x;
        BigDecimal xSquared = x.pow(2, mathContext);

        for (int n = 1; n <= 100; n++) {
            term = term.multiply(xSquared.negate(), mathContext);
            term = term.divide(BigDecimal.valueOf((2 * n) * (2 * n + 1)), mathContext);

            result = result.add(term, mathContext);
        }
        return result;
    }

    public boolean checkPointInArea(BigDecimal x, BigDecimal y, BigDecimal r) {
        ArrayList<Point> polygon = new ArrayList<>() {};

        polygon.add(new Point(r, BigDecimal.ZERO));
        polygon.add(new Point(BigDecimal.ZERO, r.divide(TWO, mathContext)));
        polygon.add(new Point(BigDecimal.ZERO, BigDecimal.ZERO));
        polygon.add(new Point(r.negate(), BigDecimal.ZERO));

        for (double angle = 180; angle <= 270; angle += 1) {
            polygon.add(new Point(
                    r.multiply(cos(BigDecimal.valueOf(Math.toRadians(angle)))),
                    r.multiply(sin(BigDecimal.valueOf(Math.toRadians(angle))))));
        }

        polygon.add(new Point(BigDecimal.ZERO, r.divide(TWO, mathContext).negate()));
        polygon.add(new Point(r, r.divide(TWO, mathContext).negate()));
        polygon.add(new Point(r, BigDecimal.ZERO));

        boolean isPointInArea = false;

        int n = polygon.size();

        for (int i = 0, j = n - 1; i < n; j = i++) {
            BigDecimal xi = polygon.get(i).x;
            BigDecimal yi = polygon.get(i).y;
            BigDecimal xj = polygon.get(j).x;
            BigDecimal yj = polygon.get(j).y;

            if (xi.compareTo(x) == 0 && yi.compareTo(y) == 0) {
                return true;
            }

            // (yi > y != yj > y) && (x < xj + (xi - xj) * (y - yj) / (yi - yj))
            if (((yi.compareTo(y) > 0) != (yj.compareTo(y) > 0)) &&
                    (x.compareTo(xj.add(xi.subtract(xj).multiply(y.subtract(yj)).divide(yi.subtract(yj), mathContext))) < 0)) {
                isPointInArea = !isPointInArea;
            }
        }

        return isPointInArea;
    }

    public BigDecimal translateX(String x, String r) {
        return HUNDRED_FIFTY.add(new BigDecimal(x).divide(new BigDecimal(r), mathContext).multiply(HUNDRED));
    }

    public BigDecimal translateY(String y, String r) {
        return HUNDRED_FIFTY.add(new BigDecimal(y).divide(new BigDecimal(r), mathContext).multiply(HUNDRED).negate());
    }
}
