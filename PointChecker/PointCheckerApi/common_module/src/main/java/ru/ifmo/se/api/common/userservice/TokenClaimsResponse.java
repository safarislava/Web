package ru.ifmo.se.api.common.userservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenClaimsResponse {
    private Long userId;
    private String issuedTime;
}