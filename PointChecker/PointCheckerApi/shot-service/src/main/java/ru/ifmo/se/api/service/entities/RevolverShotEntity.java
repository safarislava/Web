package ru.ifmo.se.api.service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "revolver_shot")
@PrimaryKeyJoinColumn(name = "id")
public class RevolverShotEntity extends ShotEntity {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BulletEntity bullet;

    public RevolverShotEntity() {}

    public <R> R accept(ShotEntityVisitor<R> visitor){
        return visitor.visit(this);
    }
}
