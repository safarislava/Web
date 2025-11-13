package ru.ifmo.se.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.api.entities.ShotEntity;
import ru.ifmo.se.api.entities.UserEntity;

import java.util.List;

@Repository
public interface ShotRepository extends JpaRepository<ShotEntity, Long> {
    List<ShotEntity> findAllByUser(UserEntity userEntity);
    void deleteAllByUser(UserEntity userEntity);
}
