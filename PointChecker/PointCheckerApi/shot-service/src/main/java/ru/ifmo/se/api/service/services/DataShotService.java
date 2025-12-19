package ru.ifmo.se.api.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.common.dto.shot.ShotRequest;
import ru.ifmo.se.api.service.components.processors.RequestProcessor;
import ru.ifmo.se.api.service.components.processors.RequestProcessorRegistry;
import ru.ifmo.se.api.service.mappers.ShotMapper;
import ru.ifmo.se.api.service.mappers.WeaponMapper;
import ru.ifmo.se.api.service.models.Shot;
import ru.ifmo.se.api.service.models.Weapon;
import ru.ifmo.se.api.service.repositories.ShotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataShotService implements ShotService {
    private final ShotRepository shotRepository;
    private final RequestProcessorRegistry processorRegistry;
    private final ShotMapper shotMapper;
    private final WeaponMapper weaponMapper;

    public List<Shot> getShots(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return shotRepository.findAllByUserId(userId, pageable).stream().map(shotMapper::toModel).toList();
    }

    @Override
    public List<Shot> getShots(Long userId) {
        return shotRepository.findAllByUserId(userId).stream().map(shotMapper::toModel).toList();
    }

    @Override
    public Shot processShot(ShotRequest request, Long userId) {
        Weapon weapon = weaponMapper.toModel(request.getWeapon());
        RequestProcessor processor = processorRegistry.getRequestProcessor(weapon);
        Shot shot = processor.process(request.getX(), request.getY(), request.getR());
        shot.setUserId(userId);
        return shotMapper.toModel(shotRepository.save(shotMapper.toEntity(shot)));
    }

    @Override
    public void clearShots(Long userId) {
        shotRepository.deleteAllByUserId(userId);
    }
}
