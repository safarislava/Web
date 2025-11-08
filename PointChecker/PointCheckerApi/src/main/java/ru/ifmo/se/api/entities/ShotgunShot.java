package ru.ifmo.se.api.entities;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.dto.responses.BulletDto;
import ru.ifmo.se.api.dto.responses.ShotDetails;
import ru.ifmo.se.api.dto.requests.ShotRequest;

import jakarta.persistence.*;
import ru.ifmo.se.api.dto.responses.ShotgunDetails;

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
        super(user, bullets.stream().mapToInt(p -> p.getHit() ? 1 : 0).sum() * 100 / bullets.size(),
                deltaTime, shotRequest);
        this.bullets = bullets;
    }

    @Override
    public ShotDetails getDetails() {
        List<BulletDto> bullets = this.bullets.stream().map(BulletDto::new).toList();
        return new ShotgunDetails(bullets);
    }
}
