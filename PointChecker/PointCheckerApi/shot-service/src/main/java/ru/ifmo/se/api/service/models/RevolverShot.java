package ru.ifmo.se.api.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class RevolverShot extends Shot {
    private Bullet bullet;
}
