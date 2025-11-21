package ru.ifmo.se.api.coreservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.common.shotservice.Message;
import ru.ifmo.se.api.common.shotservice.MessageType;
import ru.ifmo.se.api.common.shotservice.ShotRequest;
import ru.ifmo.se.api.common.shotservice.ShotResponse;
import ru.ifmo.se.api.coreservice.config.RabbitMQConfig;
import ru.ifmo.se.api.coreservice.exceptions.BadRequestException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShotMessageService {
    private final RabbitTemplate template;
    private final ObjectMapper objectMapper;

    public ShotResponse sendAddShotRequest(ShotRequest request, Long userId) {
        Message messageRequest = new Message(MessageType.ADD_REQUEST, userId, request);

        Message messageResponse = template.convertSendAndReceiveAsType(
                RabbitMQConfig.SHOT_REQUEST_EXCHANGE,
                RabbitMQConfig.SHOT_ADD_ROUTING_KEY,
                messageRequest,
                new ParameterizedTypeReference<>() {}
        );

        if (messageResponse == null)
            throw new IllegalArgumentException("Invalid message received");
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE))
            throw new BadRequestException(messageResponse.getPayload().toString());
        return objectMapper.convertValue(messageResponse.getPayload(), ShotResponse.class);
    }

    public List<ShotResponse> sendGetShotsRequest(Long userId) {
        Message messageRequest = new Message(MessageType.GET_REQUEST, userId, null);

        Message messageResponse = template.convertSendAndReceiveAsType(
                RabbitMQConfig.SHOT_REQUEST_EXCHANGE,
                RabbitMQConfig.SHOT_GET_ROUTING_KEY,
                messageRequest,
                new ParameterizedTypeReference<>() {}
        );

        if (messageResponse == null)
            throw new IllegalArgumentException("Invalid message received");
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE))
            throw new BadRequestException(messageResponse.getPayload().toString());
        return objectMapper.convertValue(messageResponse.getPayload(), new TypeReference<>() {});
    }

    public void sendClearShotsRequest(Long userId) {
        Message messageRequest = new Message(MessageType.CLEAR_REQUEST, userId, null);

        Message messageResponse = template.convertSendAndReceiveAsType(
                RabbitMQConfig.SHOT_REQUEST_EXCHANGE,
                RabbitMQConfig.SHOT_CLEAR_ROUTING_KEY,
                messageRequest,
                new ParameterizedTypeReference<>() {}
        );

        if (messageResponse == null)
            throw new IllegalArgumentException("Invalid message received");
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE))
            throw new BadRequestException(messageResponse.getPayload().toString());
    }
}