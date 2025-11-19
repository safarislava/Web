package ru.ifmo.se.api.shotsmodule.services;

import ru.ifmo.se.api.shotsmodule.dto.ShotRequest;
import ru.ifmo.se.api.shotsmodule.models.Shot;

import java.util.List;

public interface ShotService {
    List<Shot> getShots(Long userId);
    Shot processShot(ShotRequest request, Long userId);
    void clearShots(Long userId);
}
