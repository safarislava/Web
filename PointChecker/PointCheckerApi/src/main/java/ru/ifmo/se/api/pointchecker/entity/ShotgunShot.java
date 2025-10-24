package ru.ifmo.se.api.pointchecker.entity;

import ru.ifmo.se.api.pointchecker.dto.ShotRequest;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shotgun_shot")
@PrimaryKeyJoinColumn(name = "id")
public class ShotgunShot extends Shot {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Point> points;

    public ShotgunShot() {}

    public ShotgunShot(List<Point> points, Integer deltaTime, ShotRequest shotRequest) {
        super(points.stream().mapToInt(p -> p.getPointInArea() ? 1 : 0).sum() * 100 / points.size(),
                deltaTime, shotRequest);
        this.points = points;
    }
}
