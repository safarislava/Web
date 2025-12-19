package ru.ifmo.se.api.common.dto.shot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RevolverDetails extends ShotDetails {
    private BulletDto bullet;

    {
        setType("Revolver");
    }

    @JsonCreator
    public RevolverDetails(@JsonProperty("bullet") BulletDto bullet) {
        super("Revolver");
        this.bullet = bullet;
    }
}