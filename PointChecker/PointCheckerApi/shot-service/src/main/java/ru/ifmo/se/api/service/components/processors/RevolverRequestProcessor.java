package ru.ifmo.se.api.service.components.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.service.models.Bullet;
import ru.ifmo.se.api.service.models.RevolverShot;
import ru.ifmo.se.api.service.models.Shot;
import ru.ifmo.se.api.service.models.Weapon;
import ru.ifmo.se.api.service.services.BulletService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class RevolverRequestProcessor implements RequestProcessor {
    private final BulletService bulletService;

    @Override
    public Shot process(BigDecimal x, BigDecimal y,  BigDecimal r) {
        long startTime = System.nanoTime();
        Bullet bullet = bulletService.calculateBullet(x, y, r);
        int accuracy = bullet.getHit() ? 100 : 0;
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return RevolverShot.builder().x(x).y(y).r(r).accuracy(accuracy).deltaTime(deltaTime)
                .time(Timestamp.from(Instant.now())).bullet(bullet).build();
    }

    @Override
    public Weapon getWeapon() {
        return Weapon.REVOLVER;
    }
}
