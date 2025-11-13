package ru.ifmo.se.api.entities;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.dto.responses.BulletDto;
import ru.ifmo.se.api.dto.responses.RevolverDetailsDto;
import ru.ifmo.se.api.dto.responses.ShotDetailsDto;
import ru.ifmo.se.api.dto.requests.ShotRequest;

import jakarta.persistence.*;
import ru.ifmo.se.api.models.RevolverShot;
import ru.ifmo.se.api.models.Shot;
import ru.ifmo.se.api.models.ShotgunShot;

@Getter
@Setter
@Entity
@Table(name = "revolver_shot")
@PrimaryKeyJoinColumn(name = "id")
public class RevolverShotEntity extends ShotEntity {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BulletEntity bullet;

    public RevolverShotEntity() {}

    public RevolverShotEntity(RevolverShot revolverShot) {
        super(revolverShot);
        bullet = new BulletEntity(revolverShot.getBullet());
    }

    @Override
    public Shot toModel() {
        return new RevolverShot(this);
    }
}
