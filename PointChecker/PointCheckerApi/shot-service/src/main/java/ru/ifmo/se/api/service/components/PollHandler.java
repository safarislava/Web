package ru.ifmo.se.api.service.components;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.dto.shot.Message;
import ru.ifmo.se.api.common.dto.shot.MessageType;
import ru.ifmo.se.api.common.dto.shot.ShotResponse;
import ru.ifmo.se.api.service.config.RabbitMQConfig;
import ru.ifmo.se.api.service.mappers.ShotMapper;
import ru.ifmo.se.api.service.models.Shot;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PollHandler {
    private final RabbitTemplate template;
    private final ShotMapper shotMapper;

    public void notifyUpdate(Long userId, List<Shot> shots) {
        List<ShotResponse> shotResponses = shots.stream().map(shotMapper::toResponse).toList();
        Message message = new Message(MessageType.SUCCESS_RESPONSE, userId, shotResponses);

        template.convertAndSend(
                RabbitMQConfig.SHOT_RESPONSE_EXCHANGE,
                RabbitMQConfig.SHOT_POLL_ROUTING_KEY,
                message
        );
    }
}
