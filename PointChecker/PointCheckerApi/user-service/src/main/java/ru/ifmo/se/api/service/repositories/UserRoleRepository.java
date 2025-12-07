package ru.ifmo.se.api.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.api.service.entities.UserRoleEntity;
import ru.ifmo.se.api.service.entities.UserRoleId;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {
    @Query("SELECT r FROM UserRoleEntity r WHERE r.id.userId = :userId")
    List<UserRoleEntity> findAllByUserId(@Param("userId") Long userId);
}
