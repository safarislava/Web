package ru.ifmo.se.api.pointchecker.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.pointchecker.dto.ShotDetails;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "shot")
@Inheritance(strategy = InheritanceType.JOINED)
public class Shot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(precision = 25, scale = 20)
    private BigDecimal x;
    @Column(precision = 25, scale = 20)
    private BigDecimal y;
    @Column(precision = 25, scale = 20)
    private BigDecimal r;
    private Integer accuracy;
    private Integer deltaTime;
    private Timestamp time;

    public Shot() {}

    public Shot(ShotRequest shotRequest){
        x = shotRequest.x;
        y = shotRequest.y;
        r = shotRequest.r;
    }

    public Shot(User user, Integer accuracy, Integer deltaTime, ShotRequest shotRequest) {
        this(shotRequest);
        this.user = user;
        this.accuracy = accuracy;
        this.deltaTime = deltaTime;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public ShotDetails getDetails() {
        return new ShotDetails("");
    }
}
