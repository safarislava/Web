package ru.ifmo.se.api.shotsmodule.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "revolver_shot")
@PrimaryKeyJoinColumn(name = "id")
public class RevolverShotEntity extends ShotEntity {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BulletEntity bullet;

    public RevolverShotEntity() {}

    public RevolverShotEntity(Long id, Long version, BigDecimal x, BigDecimal y, BigDecimal r, Long userId,
                              Integer accuracy, Integer deltaTime, Timestamp time, BulletEntity bullet) {
        super(id, version, x, y, r, userId, accuracy, deltaTime, time);
        this.bullet = bullet;
    }

    public <R> R accept(ShotEntityVisitor<R> visitor){
        return visitor.visit(this);
    }
}
