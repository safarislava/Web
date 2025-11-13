package ru.ifmo.se.api.dto.requests;

import lombok.Getter;
import ru.ifmo.se.api.components.RequestProcessor;
import ru.ifmo.se.api.components.RevolverRequestProcessor;
import ru.ifmo.se.api.components.ShotgunRequestProcessor;

@Getter
public enum WeaponDto {
    REVOLVER(RevolverRequestProcessor.class),
    SHOTGUN(ShotgunRequestProcessor.class);

    private final Class<? extends RequestProcessor> processorClass;

    WeaponDto(Class<? extends RequestProcessor> processorClass) {
        this.processorClass = processorClass;
    }
}
