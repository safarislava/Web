package ru.ifmo.se.api.pointchecker.entity;

import ru.ifmo.se.api.pointchecker.dto.ShotRequest;

import jakarta.persistence.*;

@Entity
@Table(name = "revolver_shot")
@PrimaryKeyJoinColumn(name = "id")
public class RevolverShot extends Shot {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Point point;

    public RevolverShot() {}

    public RevolverShot(Point point, Integer deltaTime, ShotRequest shotRequest) {
        super(point.getPointInArea() ? 100 : 0, deltaTime, shotRequest);
        this.point = point;
    }
}
