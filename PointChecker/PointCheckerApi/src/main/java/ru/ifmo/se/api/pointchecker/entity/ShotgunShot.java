package ru.ifmo.se.api.pointchecker.entity;

import ru.ifmo.se.api.pointchecker.dto.ShotRequest;

import jakarta.persistence.*;

import java.util.List;

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

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }
}
