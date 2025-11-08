package ru.ifmo.se.api.pointchecker.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.pointchecker.dto.requests.ShotRequest;
import ru.ifmo.se.api.pointchecker.entities.AbstractPoint;
import ru.ifmo.se.api.pointchecker.entities.Bullet;
import ru.ifmo.se.api.pointchecker.entities.Shot;
import ru.ifmo.se.api.pointchecker.entities.User;
import ru.ifmo.se.api.pointchecker.services.CacheService;
import ru.ifmo.se.api.pointchecker.services.CalculationService;
import ru.ifmo.se.api.pointchecker.utils.BigDecimalMath;

import java.math.BigDecimal;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class RequestProcessor {
    private final CacheService cacheService;
    private final CalculationService calculationService;

    public Shot process(ShotRequest request, User user) {
        return null;
    }

    protected Bullet processShot(ShotRequest shotRequest) {
        AbstractPoint abstractPoint = addSpread(shotRequest.x, shotRequest.y, shotRequest.r);
        Boolean isPointInArea = cacheService.getCache(abstractPoint);
        if (isPointInArea == null) {
            isPointInArea = calculationService.checkPointInArea(abstractPoint.x, abstractPoint.y);
            cacheService.setCache(abstractPoint, isPointInArea);
        }
        return new Bullet(abstractPoint.x, abstractPoint.y, isPointInArea);
    }

    private AbstractPoint addSpread(BigDecimal x, BigDecimal y, BigDecimal r) {
        Random random = new Random();
        BigDecimal mod = BigDecimal.valueOf(random.nextDouble());
        BigDecimal arg = BigDecimal.valueOf(random.nextDouble(2 * Math.PI));

        BigDecimal pointX = x.add(BigDecimalMath.cos(arg).multiply(mod).multiply(r));
        BigDecimal pointY = y.add(BigDecimalMath.sin(arg).multiply(mod).multiply(r));
        return new AbstractPoint(pointX, pointY);
    }
}
