package ru.ifmo.se.api.service.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.api.service.entities.ShotEntity;

import java.util.List;

@Repository
public interface ShotRepository extends JpaRepository<ShotEntity, Long> {
    List<ShotEntity> findAllByUserId(Long userId);
    List<ShotEntity> findAllByUserId(Long userId, Pageable pageable);
    void deleteAllByUserId(Long userId);
}
