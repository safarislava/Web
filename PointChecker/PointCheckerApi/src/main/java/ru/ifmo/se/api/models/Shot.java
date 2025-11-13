package ru.ifmo.se.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.dto.responses.ShotDetails;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
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

    public Shot(BigDecimal x, BigDecimal y, BigDecimal r, Integer accuracy, Integer deltaTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.accuracy = accuracy;
        this.deltaTime = deltaTime;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public ShotDetails getDetailsDto() {
        return new ShotDetails("");
    }
}
