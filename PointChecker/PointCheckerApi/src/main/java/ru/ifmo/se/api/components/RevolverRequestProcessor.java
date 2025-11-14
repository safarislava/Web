package ru.ifmo.se.api.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.models.Bullet;
import ru.ifmo.se.api.models.RevolverShot;
import ru.ifmo.se.api.models.Shot;
import ru.ifmo.se.api.services.BulletService;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class RevolverRequestProcessor implements RequestProcessor {
    private final BulletService bulletService;

    @Override
    public Shot process(BigDecimal x, BigDecimal y,  BigDecimal r) {
        long startTime = System.nanoTime();
        Bullet bullet = bulletService.calculateBullet(x, y, r);
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new RevolverShot(x, y, r, deltaTime, bullet);
    }
}
