package ru.ifmo.se.api.entities;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.dto.responses.BulletDto;
import ru.ifmo.se.api.dto.responses.RevolverDetails;
import ru.ifmo.se.api.dto.responses.ShotDetails;
import ru.ifmo.se.api.dto.requests.ShotRequest;

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
        super(user, bullet.getHit() ? 100 : 0, deltaTime, shotRequest);
        this.bullet = bullet;
    }

    @Override
    public ShotDetails getDetails() {
        BulletDto bullet = new BulletDto(this.bullet);
        return new RevolverDetails(bullet);
    }
}
