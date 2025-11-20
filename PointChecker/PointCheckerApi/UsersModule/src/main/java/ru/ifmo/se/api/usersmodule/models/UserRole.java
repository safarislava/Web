package ru.ifmo.se.api.usersmodule.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRole {
    private Long userId;
    private Role role;
}
