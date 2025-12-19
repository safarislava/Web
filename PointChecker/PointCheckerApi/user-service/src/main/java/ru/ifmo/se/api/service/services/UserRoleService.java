package ru.ifmo.se.api.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.service.entities.UserRoleEntity;
import ru.ifmo.se.api.service.mappers.UserRoleMapper;
import ru.ifmo.se.api.service.models.Role;
import ru.ifmo.se.api.service.models.UserRole;
import ru.ifmo.se.api.service.repositories.UserRoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;

    public List<Role> getRoles(Long userId) {
        List<UserRoleEntity> userRolesEntity = userRoleRepository.findAllByUserId(userId);
        return userRolesEntity.stream()
                .map(userRoleMapper::toModel)
                .map(UserRole::getRole).toList();
    }

    public void addRole(Long userId, Role role) {
        userRoleRepository.save(userRoleMapper.toEntity(new UserRole(userId, role)));
    }
}
