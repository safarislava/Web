package ru.ifmo.se.api.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.entities.AbstractPoint;
import ru.ifmo.se.api.entities.Bullet;
import ru.ifmo.se.api.entities.Shot;
import ru.ifmo.se.api.entities.User;
import ru.ifmo.se.api.services.CacheService;
import ru.ifmo.se.api.services.CalculationService;
import ru.ifmo.se.api.utils.BigDecimalMath;

import java.math.BigDecimal;
import java.util.Optional;
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
        Optional<Boolean> hit = cacheService.getCache(abstractPoint);
        if (hit.isEmpty()) {
            hit = Optional.of(calculationService.checkPointInArea(abstractPoint.x, abstractPoint.y));
            cacheService.setCache(abstractPoint, hit.get());
        }
        return new Bullet(abstractPoint.x, abstractPoint.y, hit.get());
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
