package ru.ifmo.se.api.entities;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.dto.responses.BulletDto;
import ru.ifmo.se.api.dto.responses.ShotDetailsDto;
import ru.ifmo.se.api.dto.requests.ShotRequest;

import jakarta.persistence.*;
import ru.ifmo.se.api.dto.responses.ShotgunDetailsDto;
import ru.ifmo.se.api.models.RevolverShot;
import ru.ifmo.se.api.models.Shot;
import ru.ifmo.se.api.models.ShotgunShot;

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

    public ShotgunShotEntity(ShotgunShot shotgunShot) {
        super(shotgunShot);
        bullets = shotgunShot.getBullets().stream().map(BulletEntity::new).toList();
    }

    @Override
    public Shot toModel() {
        return new ShotgunShot(this);
    }
}
