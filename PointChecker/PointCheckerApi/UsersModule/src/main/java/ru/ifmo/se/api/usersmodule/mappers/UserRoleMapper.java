package ru.ifmo.se.api.usersmodule.mappers;

import ru.ifmo.se.api.usersmodule.entities.UserRoleEntity;
import ru.ifmo.se.api.usersmodule.models.Role;
import ru.ifmo.se.api.usersmodule.models.UserRole;

public class UserRoleMapper {
    public static UserRole toModel(UserRoleEntity userRoleEntity) {
        return new UserRole(userRoleEntity.getUserId(), Role.valueOf(userRoleEntity.getRole()));
    }

    public static UserRoleEntity toEntity(UserRole userRole) {
        return new UserRoleEntity(userRole.getUserId(), userRole.getRole().toString());
    }
}
