package ru.ifmo.se.api.usersmodule.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import ru.ifmo.se.api.usersmodule.components.JwtComponent;
import ru.ifmo.se.api.usersmodule.config.RabbitMQConfig;
import ru.ifmo.se.api.usersmodule.dto.Message;
import ru.ifmo.se.api.usersmodule.dto.MessageType;
import ru.ifmo.se.api.usersmodule.dto.TokenClaimsResponse;

import java.time.Instant;

@Controller
@RequiredArgsConstructor
public class TokenController {
    private final ObjectMapper objectMapper;
    private final JwtComponent jwtComponent;

    @RabbitListener(queues = RabbitMQConfig.TOKEN_PARSE_CLAIMS_QUEUE)
    public Message parseClaims(Message message) {
        try {
            String token = objectMapper.convertValue(message.getPayload(), String.class);
            if (!jwtComponent.verify(token)) throw new IllegalArgumentException("Invalid token");

            Long userId = jwtComponent.getUserId(token);
            Instant issuedTime = jwtComponent.getIssuedTime(token);
            TokenClaimsResponse response = new TokenClaimsResponse(userId, issuedTime.toString());

            return new Message(MessageType.SUCCESS_RESPONSE, response);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }
}
