package ru.ifmo.se.api.pointchecker.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.pointchecker.utils.SHA256;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @Column(unique = true)
    private String username;
    private String password;
    private String salt;

    public User() {}

    public User(String username, String password) {
        salt = UUID.randomUUID().toString();
        this.username = username;
        this.password = SHA256.getHash(password + salt);
    }
}
