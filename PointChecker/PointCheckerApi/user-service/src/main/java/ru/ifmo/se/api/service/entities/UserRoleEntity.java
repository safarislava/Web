package ru.ifmo.se.api.service.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_role")
@NoArgsConstructor
public class UserRoleEntity {
    @EmbeddedId
    private UserRoleId id;

    public UserRoleEntity(Long userId, String role) {
        id = new UserRoleId(userId, role);
    }

    public Long getUserId() {
        return id.getUserId();
    }

    public String getRole() {
        return id.getRole();
    }
}
