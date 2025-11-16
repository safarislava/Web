package ru.ifmo.se.api.shotsmodule.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.shotsmodule.models.Bullet;
import ru.ifmo.se.api.shotsmodule.models.RevolverShot;
import ru.ifmo.se.api.shotsmodule.models.Shot;
import ru.ifmo.se.api.shotsmodule.services.BulletService;

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
