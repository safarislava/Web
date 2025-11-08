package ru.ifmo.se.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.api.entities.Shot;
import ru.ifmo.se.api.entities.User;

import java.util.List;

@Repository
public interface ShotRepository extends JpaRepository<Shot, Long> {
    List<Shot> findAllByUser(User user);
    void deleteAllByUser(User user);
}
