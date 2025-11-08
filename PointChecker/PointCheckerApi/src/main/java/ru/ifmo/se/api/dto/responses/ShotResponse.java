package ru.ifmo.se.api.dto.responses;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.se.api.entities.Shot;

@Setter
@Getter
public class ShotResponse {
    private Long id;
    private String x;
    private String y;
    private String r;
    private Integer accuracy;
    private Integer deltaTime;
    private String time;
    private ShotDetails details;

    public ShotResponse(Shot shot) {
        this.id = shot.getId();
        this.x = shot.getX().stripTrailingZeros().toPlainString();
        this.y = shot.getY().stripTrailingZeros().toPlainString();
        this.r = shot.getR().stripTrailingZeros().toPlainString();
        this.accuracy = shot.getAccuracy();
        this.deltaTime = shot.getDeltaTime();
        this.time = shot.getTime().toString();
        this.details = shot.getDetails();
    }
}
