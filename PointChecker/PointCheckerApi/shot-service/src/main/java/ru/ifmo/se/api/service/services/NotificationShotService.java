package ru.ifmo.se.api.service.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.common.dto.shot.ShotRequest;
import ru.ifmo.se.api.service.components.PollHandler;
import ru.ifmo.se.api.service.models.Shot;

import java.util.Collections;
import java.util.List;

@Service
@Primary
public class NotificationShotService implements ShotService {
    private final PollHandler pollHandler;
    private final ShotService shotService;

    public NotificationShotService(PollHandler pollHandler, @Qualifier("dataShotService") ShotService shotService) {
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
