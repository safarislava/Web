package ru.ifmo.se.api.service.models;

import lombok.Getter;
import ru.ifmo.se.api.service.components.processors.RequestProcessor;
import ru.ifmo.se.api.service.components.processors.RevolverRequestProcessor;
import ru.ifmo.se.api.service.components.processors.ShotgunRequestProcessor;

@Getter
public enum Weapon {
    REVOLVER(RevolverRequestProcessor.class),
    SHOTGUN(ShotgunRequestProcessor.class);

    private final Class<? extends RequestProcessor> processor;

    Weapon(Class<? extends RequestProcessor> processor) {
        this.processor = processor;
    }

    public Class<? extends RequestProcessor> getProcessorClass() {
        return processor;
    }
}

