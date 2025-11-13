package ru.ifmo.se.api.models;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.entities.UserEntity;

import java.time.Instant;

@Getter
@Setter
public class User {
    private Long id;
    private Long version;
    private String username;
    private String password;
    private Instant lastUpdate;

    public User(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.version = userEntity.getVersion();
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.lastUpdate = userEntity.getLastUpdate();
    }
}
