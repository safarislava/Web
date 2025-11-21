package ru.ifmo.se.api.common.shotservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevolverDetails extends ShotDetails {
    private BulletDto bullet;

    @JsonCreator
    public RevolverDetails(@JsonProperty("bullet") BulletDto bullet) {
        super("Revolver");
        this.bullet = bullet;
    }
}