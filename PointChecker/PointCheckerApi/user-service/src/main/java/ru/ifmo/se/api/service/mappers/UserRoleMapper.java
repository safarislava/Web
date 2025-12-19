package ru.ifmo.se.api.service.mappers;

import org.mapstruct.Mapper;
import ru.ifmo.se.api.service.entities.UserRoleEntity;
import ru.ifmo.se.api.service.models.UserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRole toModel(UserRoleEntity userRoleEntity);
    UserRoleEntity toEntity(UserRole userRole);
}
