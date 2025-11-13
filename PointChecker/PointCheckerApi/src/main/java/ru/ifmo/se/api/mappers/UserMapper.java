package ru.ifmo.se.api.mappers;

import ru.ifmo.se.api.entities.UserEntity;
import ru.ifmo.se.api.models.User;

import java.time.Instant;

public class UserMapper {
    public static User toModel(UserEntity userEntity) {
        Long id = userEntity.getId();
        Long version = userEntity.getVersion();
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        Instant lastUpdate = userEntity.getLastUpdate();
        return new User(id, version, username, password, lastUpdate);
    }

    public static UserEntity toEntity(User user) {
        Long id = user.getId();
        Long version = user.getVersion();
        String username = user.getUsername();
        String password = user.getPassword();
        Instant lastUpdate = user.getLastUpdate();
        return new UserEntity(id, version, username, password, lastUpdate);
    }
}
