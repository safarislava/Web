package ru.ifmo.se.api.service.components.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.se.api.common.dto.shot.Message;
import ru.ifmo.se.api.common.dto.shot.MessageType;
import ru.ifmo.se.api.service.config.RabbitMQConfig;
import ru.ifmo.se.api.service.services.ShotService;

@Component
public class ShotClearListener {
    private final ShotService shotService;

    public ShotClearListener(@Qualifier("shotServiceProxy") ShotService shotService) {
        this.shotService = shotService;
    }

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.SHOT_CLEAR_QUEUE)
    public Message clear(Message message) {
        try {
            shotService.clearShots(message.getUserId());
            return new Message(MessageType.SUCCESS_RESPONSE, message.getUserId(), null);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, null, e.getMessage());
        }
    }
}
