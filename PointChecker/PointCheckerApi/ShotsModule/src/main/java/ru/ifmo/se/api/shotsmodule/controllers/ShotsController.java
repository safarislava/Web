package ru.ifmo.se.api.shotsmodule.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ShotsController {
    private final ShotService shotService;
    private final ObjectMapper objectMapper;

    public ShotsController(@Qualifier("shotServiceProxy") ShotService shotService, ObjectMapper objectMapper) {
        this.shotService = shotService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.SHOT_ADD_QUEUE)
    public Message add(Message message) {
        try {
            ShotRequest shotRequest = objectMapper.convertValue(message.getPayload(), new TypeReference<>() {});
            Shot shot = shotService.processShot(shotRequest, message.getUserId());

            ShotResponse response = ShotMapper.toResponse(shot);
            return new Message(MessageType.SUCCESS_RESPONSE, message.getUserId(), response);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, null, e.getMessage());
        }
    }

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.SHOT_GET_QUEUE)
    public Message get(Message message) {
        try {
//            int page = 0;
//            List<ShotResponse> shots = new ArrayList<>();
//            List<ShotResponse> shotsPage;
//            do {
//                shotsPage = shotService.getShots(message.getUserId(), page, 10)
//                        .stream().map(ShotMapper::toResponse).toList();
//                Message response = new Message(MessageType.SUCCESS_RESPONSE, message.getUserId(), shotsPage);
//                template.convertAndSend(RabbitMQConfig.SHOT_EVENTS_EXCHANGE, "", response);
//                shots.addAll(shotsPage);
//                page++;
//            } while (shotsPage.size() == 10);
            List<ShotResponse> shots = shotService.getShots(message.getUserId())
                    .stream().map(ShotMapper::toResponse).toList();
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