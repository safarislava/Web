package ru.ifmo.se.api.shotsmodule.dto;

import lombok.Getter;
import ru.ifmo.se.api.shotsmodule.components.RequestProcessor;
import ru.ifmo.se.api.shotsmodule.components.RevolverRequestProcessor;
import ru.ifmo.se.api.shotsmodule.components.ShotgunRequestProcessor;

@Getter
public enum WeaponDto {
    REVOLVER(RevolverRequestProcessor.class),
    SHOTGUN(ShotgunRequestProcessor.class);

    private final Class<? extends RequestProcessor> processorClass;

    WeaponDto(Class<? extends RequestProcessor> processorClass) {
        this.processorClass = processorClass;
    }
}
