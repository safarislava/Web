package ru.ifmo.se.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnAuthorizationException extends RuntimeException {
    public UnAuthorizationException(String message) {
        super(message);
    }
}
