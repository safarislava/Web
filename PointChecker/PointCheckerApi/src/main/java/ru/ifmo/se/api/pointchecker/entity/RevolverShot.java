package ru.ifmo.se.api.pointchecker.entity;

import ru.ifmo.se.api.pointchecker.dto.ShotRequest;

import jakarta.persistence.*;

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

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
}
