package ru.ifmo.se.api.service.components.listeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.se.api.common.dto.shot.Message;
import ru.ifmo.se.api.common.dto.shot.MessageType;
import ru.ifmo.se.api.common.dto.shot.ShotRequest;
import ru.ifmo.se.api.common.dto.shot.ShotResponse;
import ru.ifmo.se.api.service.components.ShotRequestValidator;
import ru.ifmo.se.api.service.config.RabbitMQConfig;
import ru.ifmo.se.api.service.mappers.ShotMapper;
import ru.ifmo.se.api.service.models.Shot;
import ru.ifmo.se.api.service.services.ShotService;

@Component
public class ShotAddListener {
    private final ShotService shotService;
    private final ShotRequestValidator shotRequestValidator;
    private final ObjectMapper objectMapper;

    public ShotAddListener(
            @Qualifier("shotServiceProxy") ShotService shotService,
            ShotRequestValidator validator,
            ObjectMapper objectMapper
    ) {
        this.shotService = shotService;
        this.shotRequestValidator = validator;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.SHOT_ADD_QUEUE)
    public Message add(Message message) {
        try {
            ShotRequest shotRequest = objectMapper.convertValue(message.getPayload(), new TypeReference<>() {});
            shotRequestValidator.validate(shotRequest);

            Shot shot = shotService.processShot(shotRequest, message.getUserId());

            ShotResponse response = ShotMapper.toResponse(shot);
            return new Message(MessageType.SUCCESS_RESPONSE, message.getUserId(), response);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, null, e.getMessage());
        }
    }
}
