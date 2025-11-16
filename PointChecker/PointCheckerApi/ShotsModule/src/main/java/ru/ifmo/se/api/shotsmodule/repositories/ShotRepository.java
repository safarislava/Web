package ru.ifmo.se.api.shotsmodule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.api.shotsmodule.entities.ShotEntity;

import java.util.List;

@Repository
public interface ShotRepository extends JpaRepository<ShotEntity, Long> {
    List<ShotEntity> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
