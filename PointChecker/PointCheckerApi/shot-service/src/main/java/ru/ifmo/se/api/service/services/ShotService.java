package ru.ifmo.se.api.service.services;

import ru.ifmo.se.api.common.dto.shot.ShotRequest;
import ru.ifmo.se.api.service.models.Shot;

import java.util.List;

public interface ShotService {
    List<Shot> getShots(Long userId);
    Shot processShot(ShotRequest request, Long userId);
    void clearShots(Long userId);
}
