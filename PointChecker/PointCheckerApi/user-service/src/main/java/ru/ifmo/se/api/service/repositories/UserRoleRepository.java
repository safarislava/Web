package ru.ifmo.se.api.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.api.service.entities.UserRoleEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<List<UserRoleEntity>> findAllById_UserId(Long userId);
}
