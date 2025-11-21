package ru.ifmo.se.api.service.components;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.shotservice.Message;
import ru.ifmo.se.api.common.shotservice.MessageType;
import ru.ifmo.se.api.common.shotservice.ShotResponse;
import ru.ifmo.se.api.service.config.RabbitMQConfig;
import ru.ifmo.se.api.service.mappers.ShotMapper;
import ru.ifmo.se.api.service.models.Shot;

import java.util.List;

@Component
public class PollHandler {
    private final RabbitTemplate template;

    public PollHandler(RabbitTemplate template) {
        this.template = template;
    }

    public void notifyUpdate(Long userId, List<Shot> shots) {
        List<ShotResponse> shotResponses = shots.stream().map(ShotMapper::toResponse).toList();
        Message message = new Message(MessageType.SUCCESS_RESPONSE, userId, shotResponses);

        template.convertAndSend(
                RabbitMQConfig.SHOT_RESPONSE_EXCHANGE,
                RabbitMQConfig.SHOT_POLL_ROUTING_KEY,
                message
        );
    }
}
