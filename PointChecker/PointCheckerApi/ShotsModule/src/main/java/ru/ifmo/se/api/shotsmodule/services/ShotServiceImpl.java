package ru.ifmo.se.api.shotsmodule.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.shotsmodule.components.RequestProcessor;
import ru.ifmo.se.api.shotsmodule.dto.ShotRequest;
import ru.ifmo.se.api.shotsmodule.mappers.ShotMapper;
import ru.ifmo.se.api.shotsmodule.models.Shot;
import ru.ifmo.se.api.shotsmodule.repositories.ShotRepository;

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
        Class<? extends RequestProcessor> processorClass = request.getWeapon().getProcessorClass();
        return applicationContext.getBean(processorClass);
    }
}
