package ru.ifmo.se.api.shotsmodule.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.shotsmodule.components.PollHandler;
import ru.ifmo.se.api.shotsmodule.dto.ShotRequest;
import ru.ifmo.se.api.shotsmodule.models.Shot;

import java.util.Collections;
import java.util.List;

@Service
public class ShotServiceProxy implements ShotService {
    private final PollHandler pollHandler;
    private final ShotService shotService;

    public ShotServiceProxy(PollHandler pollHandler, @Qualifier("shotServiceImpl") ShotService shotService) {
        this.pollHandler = pollHandler;
        this.shotService = shotService;
    }

    @Override
    public List<Shot> getShots(Long userId) {
        return shotService.getShots(userId);
    }

    @Override
    public Shot processShot(ShotRequest request, Long userId) {
        Shot shot = shotService.processShot(request, userId);

        List<Shot> shots = shotService.getShots(userId);
        pollHandler.notifyUpdate(userId, shots);
        return shot;
    }

    @Override
    public void clearShots(Long userId) {
        shotService.clearShots(userId);

        pollHandler.notifyUpdate(userId, Collections.emptyList());
    }
}
