package ru.ifmo.se.api.coremodule.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.coremodule.config.RabbitMQConfig;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.Message;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.MessageType;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.ShotRequest;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.ShotResponse;
import ru.ifmo.se.api.coremodule.exceptions.BadRequestException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShotMessageService {
    private final RabbitTemplate template;
    private final ObjectMapper objectMapper;

    public void sendAddShotRequest(ShotRequest request, Long userId) {
        Message messageRequest = new Message(MessageType.ADD_REQUEST, userId, request);

        Object response = template.convertSendAndReceive(
                RabbitMQConfig.SHOT_REQUEST_EXCHANGE,
                RabbitMQConfig.SHOT_ADD_ROUTING_KEY,
                messageRequest
        );
        Message messageResponse = objectMapper.convertValue(response, Message.class);
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE)) {
            throw new BadRequestException(messageResponse.getPayload().toString());
        }
    }

    public List<ShotResponse> sendGetShotsRequest(Long userId) {
        Message messageRequest = new Message(MessageType.GET_REQUEST, userId, null);

        Object response = template.convertSendAndReceive(
                RabbitMQConfig.SHOT_REQUEST_EXCHANGE,
                RabbitMQConfig.SHOT_GET_ROUTING_KEY,
                messageRequest
        );
        Message messageResponse = objectMapper.convertValue(response, Message.class);
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE)) {
            throw new BadRequestException(messageResponse.getPayload().toString());
        }
        return objectMapper.convertValue(messageResponse.getPayload(), new TypeReference<List<ShotResponse>>() {});
    }

    public void sendClearShotsRequest(Long userId) {
        Message messageRequest = new Message(MessageType.CLEAR_REQUEST, userId, null);

        Object response = template.convertSendAndReceive(
                RabbitMQConfig.SHOT_REQUEST_EXCHANGE,
                RabbitMQConfig.SHOT_CLEAR_ROUTING_KEY,
                messageRequest
        );
        Message messageResponse = objectMapper.convertValue(response, Message.class);
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE)) {
            throw new BadRequestException(messageResponse.getPayload().toString());
        }
    }
}