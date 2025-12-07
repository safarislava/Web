package ru.ifmo.se.api.service.components.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.se.api.common.dto.shot.Message;
import ru.ifmo.se.api.common.dto.shot.MessageType;
import ru.ifmo.se.api.common.dto.shot.ShotResponse;
import ru.ifmo.se.api.service.config.RabbitMQConfig;
import ru.ifmo.se.api.service.mappers.ShotMapper;
import ru.ifmo.se.api.service.services.ShotService;

import java.util.List;

@Component
public class ShotGetListener {
    private final ShotService shotService;

    public ShotGetListener(@Qualifier("shotServiceProxy") ShotService shotService) {
        this.shotService = shotService;
    }

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.SHOT_GET_QUEUE)
    public Message get(Message message) {
        try {
            List<ShotResponse> shots = shotService.getShots(message.getUserId())
                    .stream().map(ShotMapper::toResponse).toList();
            return new Message(MessageType.SUCCESS_RESPONSE, message.getUserId(), shots);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, null, e.getMessage());
        }
    }
}
