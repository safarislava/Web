package ru.ifmo.se.api.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ShotgunShot extends Shot {
    private List<Bullet> bullets;
}
