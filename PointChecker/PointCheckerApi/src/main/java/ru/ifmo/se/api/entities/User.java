package ru.ifmo.se.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

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
    private Instant lastUpdate;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
