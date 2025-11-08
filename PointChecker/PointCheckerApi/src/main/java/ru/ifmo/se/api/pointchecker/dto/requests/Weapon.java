package ru.ifmo.se.api.pointchecker.dto.requests;

import lombok.Getter;
import ru.ifmo.se.api.pointchecker.components.RequestProcessor;
import ru.ifmo.se.api.pointchecker.components.RevolverRequestProcessor;
import ru.ifmo.se.api.pointchecker.components.ShotgunRequestProcessor;

@Getter
public enum Weapon {
    REVOLVER(RevolverRequestProcessor.class),
    SHOTGUN(ShotgunRequestProcessor.class);

    private final Class<? extends RequestProcessor> processorClass;

    Weapon(Class<? extends RequestProcessor> processorClass) {
        this.processorClass = processorClass;
    }
}
