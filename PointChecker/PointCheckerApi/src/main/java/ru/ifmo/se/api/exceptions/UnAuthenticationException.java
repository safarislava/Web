package ru.ifmo.se.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthenticationException extends RuntimeException {
    public UnAuthenticationException(String message) {
        super(message);
    }
}
