package ru.ifmo.se.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.dto.responses.ShotDetailsDto;
import ru.ifmo.se.api.dto.requests.ShotRequest;
import ru.ifmo.se.api.models.RevolverShot;
import ru.ifmo.se.api.models.Shot;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "shot")
@Inheritance(strategy = InheritanceType.JOINED)
public class ShotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    @Column(precision = 25, scale = 20)
    private BigDecimal x;
    @Column(precision = 25, scale = 20)
    private BigDecimal y;
    @Column(precision = 25, scale = 20)
    private BigDecimal r;
    private Integer accuracy;
    private Integer deltaTime;
    private Timestamp time;

    public ShotEntity() {}

    public ShotEntity(Shot shot) {
        id = shot.getId();
        version = shot.getVersion();
        x = shot.getX();
        y = shot.getY();
        r = shot.getR();
        user = new UserEntity(shot.getUser());
        accuracy = shot.getAccuracy();
        deltaTime = shot.getDeltaTime();
        time = shot.getTime();
    }

    public Shot toModel() {
        return new Shot(this);
    }
}
