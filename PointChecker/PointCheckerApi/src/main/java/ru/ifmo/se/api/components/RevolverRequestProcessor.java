package ru.ifmo.se.api.components;

import org.springframework.stereotype.Component;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.entities.Bullet;
import ru.ifmo.se.api.entities.RevolverShot;
import ru.ifmo.se.api.entities.Shot;
import ru.ifmo.se.api.entities.User;
import ru.ifmo.se.api.services.CacheService;
import ru.ifmo.se.api.services.CalculationService;

@Component
public class RevolverRequestProcessor extends RequestProcessor {
    public RevolverRequestProcessor(CacheService cacheService, CalculationService calculationService) {
        super(cacheService, calculationService);
    }

    @Override
    public Shot process(ShotRequest request, User user) {
        long startTime = System.nanoTime();
        Bullet bullet = processShot(request);
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new RevolverShot(user, bullet, deltaTime, request);
    }
}
