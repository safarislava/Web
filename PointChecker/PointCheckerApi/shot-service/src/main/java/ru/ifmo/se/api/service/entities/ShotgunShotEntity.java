package ru.ifmo.se.api.service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shotgun_shot")
@PrimaryKeyJoinColumn(name = "id")
public class ShotgunShotEntity extends ShotEntity {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BulletEntity> bullets;

    public ShotgunShotEntity() {}

    public ShotgunShotEntity(Long id, Long version, BigDecimal x, BigDecimal y, BigDecimal r, Long userId,
                              Integer accuracy, Integer deltaTime, Timestamp time, List<BulletEntity> bullets) {
        super(id, version, x, y, r, userId, accuracy, deltaTime, time);
        this.bullets = bullets;
    }

    public <R> R accept(ShotEntityVisitor<R> visitor){
        return visitor.visit(this);
    }
}
