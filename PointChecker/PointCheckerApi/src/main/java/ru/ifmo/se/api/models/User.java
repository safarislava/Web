package ru.ifmo.se.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private Long id;
    private Long version;
    private String username;
    private String password;
    private Instant lastUpdate;
}
