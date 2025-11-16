package ru.ifmo.se.api.coremodule.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.coremodule.config.RabbitMQConfig;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.Message;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.MessageType;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.ShotResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ShotEventsListener {
    private final ShotSocketHandler shotSocketHandler;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.SHOT_EVENTS_QUEUE)
    public void handleShotProcessed(Message message) {
        try {
            if (message.getMessageType().equals(MessageType.ERROR_RESPONSE)) throw new IllegalArgumentException("Invalid message type");
            ShotResponse shot = objectMapper.convertValue(message.getPayload(), new TypeReference<>() {});
            shotSocketHandler.sendShotToUser(message.getUserId(), shot);
        } catch (IOException ignored) {}
    }
}
