package ru.ifmo.se.api.usersmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokensDto {
    private String accessToken;
    private String refreshToken;
}
