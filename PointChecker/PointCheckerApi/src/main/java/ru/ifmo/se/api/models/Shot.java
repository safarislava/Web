package ru.ifmo.se.api.models;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.dto.responses.ShotDetailsDto;
import ru.ifmo.se.api.entities.ShotEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class Shot {
    private Long id;
    private Long version;
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private User user;
    private Integer accuracy;
    private Integer deltaTime;
    private Timestamp time;

    public Shot(ShotEntity shotEntity){
        id = shotEntity.getId();
        version = shotEntity.getVersion();
        user = new User(shotEntity.getUser());
        x = shotEntity.getX();
        y = shotEntity.getY();
        r = shotEntity.getR();
        accuracy = shotEntity.getAccuracy();
        deltaTime = shotEntity.getDeltaTime();
        time = shotEntity.getTime();
    }

    public Shot(BigDecimal x, BigDecimal y, BigDecimal r, User user, Integer accuracy, Integer deltaTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.user = user;
        this.accuracy = accuracy;
        this.deltaTime = deltaTime;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public ShotEntity toEntity() {
        return new ShotEntity(this);
    }

    public ShotDetailsDto getDetailsDto() {
        return new ShotDetailsDto("");
    }
}
