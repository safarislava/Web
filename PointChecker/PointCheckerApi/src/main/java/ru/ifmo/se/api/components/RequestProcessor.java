package ru.ifmo.se.api.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.models.Bullet;
import ru.ifmo.se.api.models.Point;
import ru.ifmo.se.api.entities.BulletEntity;
import ru.ifmo.se.api.entities.ShotEntity;
import ru.ifmo.se.api.entities.UserEntity;
import ru.ifmo.se.api.models.Shot;
import ru.ifmo.se.api.models.User;
import ru.ifmo.se.api.services.CalculationService;
import ru.ifmo.se.api.utils.BigDecimalMath;

import java.math.BigDecimal;
import java.util.Random;

@Component
@RequiredArgsConstructor
public abstract class RequestProcessor {
    private final CalculationService calculationService;

    public abstract Shot process(ShotRequest request, User user);

    protected Bullet processShot(ShotRequest shotRequest) {
        Point point = addSpread(shotRequest.getX(), shotRequest.getY(), shotRequest.getR());
        boolean hit = calculationService.checkHit(point.getX(), point.getY());
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
