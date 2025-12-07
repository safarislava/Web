package ru.ifmo.se.api.service.mappers;

import ru.ifmo.se.api.service.entities.UserRoleEntity;
import ru.ifmo.se.api.service.models.Role;
import ru.ifmo.se.api.service.models.UserRole;

public class UserRoleMapper {
    public static UserRole toModel(UserRoleEntity userRoleEntity) {
        return new UserRole(userRoleEntity.getUserId(), Role.valueOf(userRoleEntity.getRole()));
    }

    public static UserRoleEntity toEntity(UserRole userRole) {
        return new UserRoleEntity(userRole.getUserId(), userRole.getRole().toString());
    }
}
