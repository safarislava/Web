package ru.ifmo.se.api.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.service.components.CalculationComponent;
import ru.ifmo.se.api.service.models.Bullet;
import ru.ifmo.se.api.service.models.Point;
import ru.ifmo.se.api.service.utils.BigDecimalMath;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BulletService {
    private final CalculationComponent calculationComponent;

    public Bullet calculateBullet(BigDecimal x,  BigDecimal y, BigDecimal r) {
        Point point = addSpread(x, y, r);
        boolean hit = calculationComponent.checkHit(point);
        return new Bullet(point.getX(), point.getY(), hit);
    }

    private Point addSpread(BigDecimal x, BigDecimal y, BigDecimal r) {
        Random random = new Random();
        BigDecimal mod = BigDecimal.valueOf(random.nextDouble());
        BigDecimal arg = BigDecimal.valueOf(random.nextDouble(2 * Math.PI));

        BigDecimal pointX = x.add(BigDecimalMath.cos(arg).multiply(mod).multiply(r));
        BigDecimal pointY = y.add(BigDecimalMath.sin(arg).multiply(mod).multiply(r));
        return new Point(pointX, pointY);
    }
}
