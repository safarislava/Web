package ru.ifmo.se.api.service.components.processors;

import org.springframework.stereotype.Component;
import ru.ifmo.se.api.service.models.Weapon;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestProcessorRegistry {
    Map<Weapon, RequestProcessor> processors = new HashMap<>();

    public void addProcessor(Weapon weapon, RequestProcessor processor) {
        processors.put(weapon,  processor);
    }

    public RequestProcessor getRequestProcessor(Weapon weapon) {
        return processors.get(weapon);
    }
}
