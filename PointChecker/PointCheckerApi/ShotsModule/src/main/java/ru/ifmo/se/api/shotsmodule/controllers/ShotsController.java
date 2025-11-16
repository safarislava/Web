package ru.ifmo.se.api.shotsmodule.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.se.api.shotsmodule.config.RabbitMQConfig;
import ru.ifmo.se.api.shotsmodule.dto.Message;
import ru.ifmo.se.api.shotsmodule.dto.MessageType;
import ru.ifmo.se.api.shotsmodule.dto.ShotRequest;
import ru.ifmo.se.api.shotsmodule.dto.ShotResponse;
import ru.ifmo.se.api.shotsmodule.mappers.ShotMapper;
import ru.ifmo.se.api.shotsmodule.models.Shot;
import ru.ifmo.se.api.shotsmodule.services.ShotService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShotsController {
    private final RabbitTemplate template;
    private final ShotService shotService;
    private final ObjectMapper objectMapper;

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.SHOT_ADD_QUEUE)
    public Message add(Message message) {
        try {
            ShotRequest shotRequest = objectMapper.convertValue(message.getPayload(), new TypeReference<>() {});

            // TODO pending/confirmed/error status
            Shot shot = shotService.processShot(shotRequest, message.getUserId());
            Message messageResponse = new Message(
                    MessageType.SUCCESS_RESPONSE,
                    message.getUserId(),
                    ShotMapper.toResponse(shot)
            );
            template.convertAndSend(RabbitMQConfig.SHOT_EVENTS_EXCHANGE, "", messageResponse);

            shotService.addShot(shot);
            return new Message(MessageType.SUCCESS_RESPONSE, message.getUserId(), null);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, null, e.getMessage());
        }
    }

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.SHOT_GET_QUEUE)
    public Message get(Message message) {
        try {
            List<ShotResponse> shots = shotService.getShots(message.getUserId()).stream().map(ShotMapper::toResponse).toList();
            return new Message(MessageType.SUCCESS_RESPONSE, message.getUserId(), shots);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, null, e.getMessage());
        }
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