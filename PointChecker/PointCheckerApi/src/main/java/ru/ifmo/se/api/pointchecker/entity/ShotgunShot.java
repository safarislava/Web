package ru.ifmo.se.api.pointchecker.entity;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.pointchecker.dto.BulletDto;
import ru.ifmo.se.api.pointchecker.dto.ShotDetails;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;

import jakarta.persistence.*;
import ru.ifmo.se.api.pointchecker.dto.ShotgunDetails;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shotgun_shot")
@PrimaryKeyJoinColumn(name = "id")
public class ShotgunShot extends Shot {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bullet> bullets;

    public ShotgunShot() {}

    public ShotgunShot(User user, List<Bullet> bullets, Integer deltaTime, ShotRequest shotRequest) {
        super(user, bullets.stream().mapToInt(p -> p.getIsPointInArea() ? 1 : 0).sum() * 100 / bullets.size(),
                deltaTime, shotRequest);
        this.bullets = bullets;
    }

    @Override
    public ShotDetails getDetails() {
        List<BulletDto> bullets = this.bullets.stream().map(BulletDto::new).toList();
        return new ShotgunDetails(bullets);
    }
}
