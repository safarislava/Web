package ru.ifmo.se.api.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.se.api.service.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
