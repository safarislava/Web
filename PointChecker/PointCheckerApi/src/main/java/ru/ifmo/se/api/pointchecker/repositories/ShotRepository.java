package ru.ifmo.se.api.pointchecker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.api.pointchecker.entities.Shot;
import ru.ifmo.se.api.pointchecker.entities.User;

import java.util.List;

@Repository
public interface ShotRepository extends JpaRepository<Shot, Long> {
    List<Shot> findAllByUser(User user);
    void deleteAllByUser(User user);
}
