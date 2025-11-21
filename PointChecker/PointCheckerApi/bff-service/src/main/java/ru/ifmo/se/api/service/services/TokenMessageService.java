package ru.ifmo.se.api.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.common.dto.user.Message;
import ru.ifmo.se.api.common.dto.user.MessageType;
import ru.ifmo.se.api.common.dto.user.TokenClaimsResponse;
import ru.ifmo.se.api.service.config.RabbitMQConfig;

@Service
@RequiredArgsConstructor
public class TokenMessageService {
    private final RabbitTemplate template;
    private final ObjectMapper objectMapper;

    public TokenClaimsResponse getTokenClaims(String token) {
        Message messageRequest = new Message(MessageType.PARSE_REQUEST, token);

        Message messageResponse = template.convertSendAndReceiveAsType(
                RabbitMQConfig.TOKEN_REQUEST_EXCHANGE,
                RabbitMQConfig.TOKEN_PARSE_CLAIMS_ROUTING_KEY,
                messageRequest,
                new ParameterizedTypeReference<>() {}
        );

        if (messageResponse == null)
            throw new IllegalArgumentException("Invalid message received");
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE))
            throw new IllegalArgumentException(messageResponse.getPayload().toString());
        return objectMapper.convertValue(messageResponse.getPayload(), TokenClaimsResponse.class);
    }
}
