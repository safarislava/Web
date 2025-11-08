package ru.ifmo.se.api.pointchecker.components;

import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.pointchecker.dto.requests.ShotRequest;
import ru.ifmo.se.api.pointchecker.entities.Bullet;
import ru.ifmo.se.api.pointchecker.entities.RevolverShot;
import ru.ifmo.se.api.pointchecker.entities.Shot;
import ru.ifmo.se.api.pointchecker.entities.User;
import ru.ifmo.se.api.pointchecker.services.CacheService;
import ru.ifmo.se.api.pointchecker.services.CalculationService;

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
