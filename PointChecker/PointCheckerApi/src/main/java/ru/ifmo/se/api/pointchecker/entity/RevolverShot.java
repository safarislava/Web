package ru.ifmo.se.api.pointchecker.entity;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "revolver_shot")
@PrimaryKeyJoinColumn(name = "id")
public class RevolverShot extends Shot {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Bullet bullet;

    public RevolverShot() {}

    public RevolverShot(User user, Bullet bullet, Integer deltaTime, ShotRequest shotRequest) {
        super(user, bullet.getIsPointInArea() ? 100 : 0, deltaTime, shotRequest);
        this.bullet = bullet;
    }
}
