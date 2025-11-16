package ru.ifmo.se.api.coremodule.dto.usersmodule;

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
