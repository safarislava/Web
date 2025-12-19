package ru.ifmo.se.api.service.components.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.service.models.Bullet;
import ru.ifmo.se.api.service.models.Shot;
import ru.ifmo.se.api.service.models.ShotgunShot;
import ru.ifmo.se.api.service.models.Weapon;
import ru.ifmo.se.api.service.services.BulletService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShotgunRequestProcessor implements RequestProcessor {
    private final BulletService bulletService;

    @Override
    public Shot process(BigDecimal x, BigDecimal y, BigDecimal r) {
        long startTime = System.nanoTime();
        List<Bullet> bullets = new ArrayList<>();
        int countHits = 0;
        for (int i = 0; i < 10; i++) {
            Bullet bullet = bulletService.calculateBullet(x, y, r);
            bullets.add(bullet);
            countHits += bullet.getHit() ? 1 : 0;
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return ShotgunShot.builder().x(x).y(y).r(r).accuracy(countHits * 100 / bullets.size()).deltaTime(deltaTime)
                .time(Timestamp.from(Instant.now())).bullets(bullets).build();
    }

    @Override
    public Weapon getWeapon() {
        return Weapon.SHOTGUN;
    }
}
