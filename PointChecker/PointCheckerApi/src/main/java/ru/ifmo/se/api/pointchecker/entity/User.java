package ru.ifmo.se.api.pointchecker.entity;

import jakarta.persistence.*;
import ru.ifmo.se.api.pointchecker.utils.SHA256;

import java.util.UUID;

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

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
}
