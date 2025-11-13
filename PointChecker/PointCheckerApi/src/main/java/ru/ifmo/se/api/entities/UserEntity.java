package ru.ifmo.se.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.models.User;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @Column(unique = true)
    private String username;
    private String password;
    private Instant lastUpdate;

    public UserEntity() {}

    public UserEntity(User user) {
        id = user.getId();
        version = user.getVersion();
        username = user.getUsername();
        password = user.getPassword();
        lastUpdate = user.getLastUpdate();
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
