package ru.ifmo.se.api.common.shotservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShotgunDetails extends ShotDetails {
    private List<BulletDto> bullets;

    @JsonCreator
    public ShotgunDetails(@JsonProperty("bullets") List<BulletDto> bullets) {
        super("Shotgun");
        this.bullets = bullets;
    }
}
