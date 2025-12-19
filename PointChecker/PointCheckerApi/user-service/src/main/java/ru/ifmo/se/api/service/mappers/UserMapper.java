package ru.ifmo.se.api.service.mappers;

import org.mapstruct.Mapper;
import ru.ifmo.se.api.service.entities.UserEntity;
import ru.ifmo.se.api.service.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toModel(UserEntity userEntity);
    UserEntity toEntity(User user);
}
