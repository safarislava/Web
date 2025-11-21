package ru.ifmo.se.api.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.common.dto.shot.ShotRequest;
import ru.ifmo.se.api.service.components.processors.RequestProcessor;
import ru.ifmo.se.api.service.mappers.ShotMapper;
import ru.ifmo.se.api.service.mappers.WeaponMapper;
import ru.ifmo.se.api.service.models.Shot;
import ru.ifmo.se.api.service.repositories.ShotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShotServiceImpl implements ShotService {
    private final ApplicationContext applicationContext;
    private final ShotRepository shotRepository;

    public List<Shot> getShots(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return shotRepository.findAllByUserId(userId, pageable).stream().map(ShotMapper::toModel).toList();
    }

    @Override
    public List<Shot> getShots(Long userId) {
        return shotRepository.findAllByUserId(userId).stream().map(ShotMapper::toModel).toList();
    }

    @Override
    public Shot processShot(ShotRequest request, Long userId) {
        RequestProcessor processor = getRequestProcessor(request);
        Shot shot = processor.process(request.getX(), request.getY(), request.getR());
        shot.setUserId(userId);
        return ShotMapper.toModel(shotRepository.save(ShotMapper.toEntity(shot)));
    }

    @Override
    public void clearShots(Long userId) {
        shotRepository.deleteAllByUserId(userId);
    }

    private RequestProcessor getRequestProcessor(ShotRequest request) {
        Class<? extends RequestProcessor> processorClass = WeaponMapper.toModel(request.getWeapon()).getProcessorClass();
        return applicationContext.getBean(processorClass);
    }
}
