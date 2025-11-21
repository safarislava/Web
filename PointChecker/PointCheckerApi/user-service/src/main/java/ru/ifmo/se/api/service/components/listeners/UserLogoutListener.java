package ru.ifmo.se.api.service.components.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.userservice.Message;
import ru.ifmo.se.api.common.userservice.MessageType;
import ru.ifmo.se.api.common.userservice.TokensDto;
import ru.ifmo.se.api.service.components.JwtComponent;
import ru.ifmo.se.api.service.components.MessageBuilder;
import ru.ifmo.se.api.service.config.RabbitMQConfig;

@Component
@RequiredArgsConstructor
public class UserLogoutListener {
    private final ObjectMapper objectMapper;
    private final JwtComponent jwtComponent;
    private final MessageBuilder messageBuilder;

    @RabbitListener(queues = RabbitMQConfig.USER_LOGOUT_QUEUE)
    public Message logout(Message message) {
        try {
            TokensDto tokensRequest = objectMapper.convertValue(message.getPayload(), TokensDto.class);
            String accessToken = tokensRequest.getAccessToken();

            if (accessToken == null || accessToken.isEmpty()) throw new IllegalArgumentException("Access token is empty");
            if (!jwtComponent.verify(accessToken)) throw new IllegalArgumentException("Wrong refresh token");

            Long userId = jwtComponent.getUserId(accessToken);
            return messageBuilder.getMessage(userId);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }
}
