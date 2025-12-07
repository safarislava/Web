package ru.ifmo.se.api.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.common.dto.shot.ShotRequest;
import ru.ifmo.se.api.service.components.processors.RequestProcessor;
import ru.ifmo.se.api.service.mappers.ShotMapper;
import ru.ifmo.se.api.service.mappers.WeaponMapper;
import ru.ifmo.se.api.service.models.Shot;
import ru.ifmo.se.api.service.models.Weapon;
import ru.ifmo.se.api.service.repositories.ShotRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShotServiceImpl implements ShotService {
    private final ShotRepository shotRepository;
    private Map<Weapon, RequestProcessor> processorMap;

    @Autowired
    public void setProcessorMap(List<RequestProcessor> processors) {
        processorMap = processors.stream()
                .collect(Collectors.toMap(RequestProcessor::getWeaponType, Function.identity()));
    }

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
        return processorMap.get(WeaponMapper.toModel(request.getWeapon()));
    }
}
