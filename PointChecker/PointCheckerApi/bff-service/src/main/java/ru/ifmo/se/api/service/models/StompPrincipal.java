package ru.ifmo.se.api.service.models;

import java.security.Principal;
import java.util.Objects;

public record StompPrincipal(String name) implements Principal {
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StompPrincipal that = (StompPrincipal) o;
        return Objects.equals(name, that.name);
    }
}
