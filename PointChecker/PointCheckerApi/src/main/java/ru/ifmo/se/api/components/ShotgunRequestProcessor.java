package ru.ifmo.se.api.components;

import org.springframework.stereotype.Component;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.entities.Bullet;
import ru.ifmo.se.api.entities.Shot;
import ru.ifmo.se.api.entities.ShotgunShot;
import ru.ifmo.se.api.entities.User;
import ru.ifmo.se.api.pointchecker.entities.*;
import ru.ifmo.se.api.services.CacheService;
import ru.ifmo.se.api.services.CalculationService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShotgunRequestProcessor extends RequestProcessor {
    public ShotgunRequestProcessor(CacheService cacheService, CalculationService calculationService) {
        super(cacheService, calculationService);
    }

    @Override
    public Shot process(ShotRequest request, User user) {
        long startTime = System.nanoTime();
        List<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bullets.add(processShot(request));
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new ShotgunShot(user, bullets, deltaTime, request);
    }
}
