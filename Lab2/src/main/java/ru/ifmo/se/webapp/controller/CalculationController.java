package ru.ifmo.se.webapp.controller;

import ru.ifmo.se.webapp.entity.Point;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class CalculationController {
    private final MathContext mathContext = MathContext.DECIMAL128;

    private final BigDecimal TWO = new BigDecimal("2");
    private final BigDecimal THREE = new BigDecimal("3");
    private final BigDecimal FOUR = new BigDecimal("4");
    private final BigDecimal FIVE = new BigDecimal("5");
    private final BigDecimal SIX = new BigDecimal("6");

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
        ArrayList<Point> polygon = new ArrayList<>();

        BigDecimal rFourthNegate = r.divide(FOUR, mathContext).negate();
        BigDecimal rTheeForth = r.multiply(THREE).divide(FOUR, mathContext);
        BigDecimal rTwoThird = r.multiply(TWO).divide(THREE, mathContext);
        BigDecimal rSixthNegate = r.divide(SIX, mathContext).negate();
        BigDecimal rSecondNegate = r.divide(TWO, mathContext).negate();

        polygon.add(new Point(r, BigDecimal.ZERO));
        polygon.add(new Point(r, rFourthNegate));
        polygon.add(new Point(rTheeForth, rTwoThird.negate()));
        polygon.add(new Point(r.divide(TWO, mathContext), rTheeForth.negate()));
        polygon.add(new Point(rTheeForth, rSixthNegate));
        polygon.add(new Point(r.divide(THREE, mathContext), rFourthNegate));

        BigDecimal arcRadius = r.multiply(TWO).divide(FIVE, mathContext);
        BigDecimal halfChord = r.divide(THREE, mathContext);

        BigDecimal h = arcRadius.pow(2, mathContext).subtract(halfChord.pow(2, mathContext)).sqrt(mathContext);
        BigDecimal arcCenterY1 = rFourthNegate.add(h);

        double startAngle1 = Math.atan2(-h.doubleValue(), halfChord.doubleValue());
        double endAngle1 = Math.atan2(-h.doubleValue(), -halfChord.doubleValue());

        int arcSteps = 20;
        for (int i = 0; i <= arcSteps; i++) {
            double currentAngle = startAngle1 + (endAngle1 - startAngle1) * i / arcSteps;
            BigDecimal angleBD = new BigDecimal(currentAngle);
            BigDecimal pX = arcRadius.multiply(cos(angleBD), mathContext);
            BigDecimal pY = arcCenterY1.add(arcRadius.multiply(sin(angleBD), mathContext));
            polygon.add(new Point(pX, pY));
        }

        polygon.add(new Point(rTheeForth.negate(), rSixthNegate));
        polygon.add(new Point(rSecondNegate, rTheeForth.negate()));
        polygon.add(new Point(rTheeForth.negate(), rTwoThird.negate()));
        polygon.add(new Point(r.negate(), rFourthNegate));
        polygon.add(new Point(r.negate(), r.divide(FOUR, mathContext)));
        polygon.add(new Point(rTheeForth.negate(), rTwoThird));
        polygon.add(new Point(rSecondNegate, rTheeForth));
        polygon.add(new Point(rTheeForth.negate(), r.divide(SIX, mathContext)));
        polygon.add(new Point(r.divide(THREE, mathContext).negate(), r.divide(FOUR, mathContext)));


        BigDecimal arcCenterY2 = r.divide(FOUR, mathContext).subtract(h);

        double startAngle2 = Math.atan2(h.doubleValue(), -halfChord.doubleValue());
        double endAngle2 = Math.atan2(h.doubleValue(), halfChord.doubleValue());

        if (endAngle2 > startAngle2) {
            startAngle2 += 2 * Math.PI;
        }

        for (int i = 0; i <= arcSteps; i++) {
            double currentAngle = startAngle2 + (endAngle2 - startAngle2) * i / arcSteps;
            BigDecimal angleBD = new BigDecimal(currentAngle);
            BigDecimal pX = arcRadius.multiply(cos(angleBD), mathContext);
            BigDecimal pY = arcCenterY2.add(arcRadius.multiply(sin(angleBD), mathContext));
            polygon.add(new Point(pX, pY));
        }

        polygon.add(new Point(rTheeForth, r.divide(SIX, mathContext)));
        polygon.add(new Point(r.divide(TWO, mathContext), rTheeForth));
        polygon.add(new Point(rTheeForth, rTwoThird));
        polygon.add(new Point(r, r.divide(FOUR, mathContext)));

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

            if (((yi.compareTo(y) > 0) != (yj.compareTo(y) > 0)) &&
                    (x.compareTo(xj.add(
                            xi.subtract(xj)
                                    .multiply(y.subtract(yj))
                                    .divide(yi.subtract(yj), mathContext)
                    )) < 0)) {
                isPointInArea = !isPointInArea;
            }
        }

        return isPointInArea;
    }
}
