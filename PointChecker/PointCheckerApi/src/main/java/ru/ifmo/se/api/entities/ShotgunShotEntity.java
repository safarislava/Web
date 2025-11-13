package ru.ifmo.se.api.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import ru.ifmo.se.api.mappers.BulletMapper;
import ru.ifmo.se.api.models.Shot;
import ru.ifmo.se.api.models.ShotgunShot;

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

    public ShotgunShotEntity(Long id, Long version, BigDecimal x, BigDecimal y, BigDecimal r, UserEntity user,
                              Integer accuracy, Integer deltaTime, Timestamp time, List<BulletEntity> bullets) {
        super(id, version, x, y, r, user, accuracy, deltaTime, time);
        this.bullets = bullets;
    }
}
