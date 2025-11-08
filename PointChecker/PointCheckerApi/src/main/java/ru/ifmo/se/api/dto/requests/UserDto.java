package ru.ifmo.se.api.dto.requests;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDto {
    public String username;
    public String password;

    public UserDto() {}
}
