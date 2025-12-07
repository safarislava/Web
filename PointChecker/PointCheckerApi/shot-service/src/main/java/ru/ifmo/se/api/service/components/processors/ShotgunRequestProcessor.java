package ru.ifmo.se.api.service.components.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.service.models.Bullet;
import ru.ifmo.se.api.service.models.Shot;
import ru.ifmo.se.api.service.models.ShotgunShot;
import ru.ifmo.se.api.service.models.Weapon;
import ru.ifmo.se.api.service.services.BulletService;

import java.math.BigDecimal;
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
        for (int i = 0; i < 10; i++) {
            bullets.add(bulletService.calculateBullet(x, y, r));
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new ShotgunShot(x, y, r, deltaTime, bullets);
    }

    @Override
    public Weapon getWeaponType() {
        return Weapon.SHOTGUN;
    }
}
