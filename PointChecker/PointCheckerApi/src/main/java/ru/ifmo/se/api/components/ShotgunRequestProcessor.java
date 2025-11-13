package ru.ifmo.se.api.components;

import org.springframework.stereotype.Component;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.models.Bullet;
import ru.ifmo.se.api.models.Shot;
import ru.ifmo.se.api.models.ShotgunShot;
import ru.ifmo.se.api.services.CalculationService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShotgunRequestProcessor extends RequestProcessor {
    public ShotgunRequestProcessor(CalculationService calculationService) {
        super(calculationService);
    }

    @Override
    public Shot process(ShotRequest request) {
        long startTime = System.nanoTime();
        List<Bullet> bullets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bullets.add(processShot(request));
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new ShotgunShot(request.getX(), request.getY(), request.getR(), deltaTime, bullets);
    }
}
