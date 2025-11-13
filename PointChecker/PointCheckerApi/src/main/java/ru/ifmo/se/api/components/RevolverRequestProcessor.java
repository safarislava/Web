package ru.ifmo.se.api.components;

import org.springframework.stereotype.Component;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.models.Bullet;
import ru.ifmo.se.api.models.RevolverShot;
import ru.ifmo.se.api.models.Shot;
import ru.ifmo.se.api.models.User;
import ru.ifmo.se.api.services.CalculationService;

@Component
public class RevolverRequestProcessor extends RequestProcessor {
    public RevolverRequestProcessor(CalculationService calculationService) {
        super(calculationService);
    }

    @Override
    public Shot process(ShotRequest request, User user) {
        long startTime = System.nanoTime();
        Bullet bullet = processShot(request);
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new RevolverShot(request.getX(), request.getY(), request.getR(), user, deltaTime, bullet);
    }
}
