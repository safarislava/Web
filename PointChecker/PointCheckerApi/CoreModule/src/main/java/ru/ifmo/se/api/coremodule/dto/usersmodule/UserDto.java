package ru.ifmo.se.api.coremodule.dto.usersmodule;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
}
