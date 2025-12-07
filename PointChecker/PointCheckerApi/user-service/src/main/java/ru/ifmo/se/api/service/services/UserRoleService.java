package ru.ifmo.se.api.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.service.entities.UserRoleEntity;
import ru.ifmo.se.api.service.mappers.UserRoleMapper;
import ru.ifmo.se.api.service.models.Role;
import ru.ifmo.se.api.service.models.UserRole;
import ru.ifmo.se.api.service.repositories.UserRoleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public List<Role> getRoles(Long userId) {
        Optional<List<UserRoleEntity>> userRolesEntity = userRoleRepository.findAllById_UserId(userId);
        return userRolesEntity.map(
                userRoleEntityList -> userRoleEntityList.stream()
                        .map(UserRoleMapper::toModel)
                        .map(UserRole::getRole).toList())
                .orElse(Collections.emptyList());
    }

    public void addRole(Long userId, Role role) {
        userRoleRepository.save(UserRoleMapper.toEntity(new UserRole(userId, role)));
    }
}
