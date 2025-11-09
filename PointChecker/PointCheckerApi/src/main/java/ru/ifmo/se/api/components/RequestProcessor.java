package ru.ifmo.se.api.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.entities.Point;
import ru.ifmo.se.api.entities.Bullet;
import ru.ifmo.se.api.entities.Shot;
import ru.ifmo.se.api.entities.User;
import ru.ifmo.se.api.services.CalculationService;
import ru.ifmo.se.api.utils.BigDecimalMath;

import java.math.BigDecimal;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class RequestProcessor {
    private final CalculationService calculationService;

    public Shot process(ShotRequest request, User user) {
        return null;
    }

    protected Bullet processShot(ShotRequest shotRequest) {
        Point point = addSpread(shotRequest.x, shotRequest.y, shotRequest.r);
        boolean hit = calculationService.checkHit(point.x, point.y);
        return new Bullet(point.x, point.y, hit);
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
