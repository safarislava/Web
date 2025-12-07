package ru.ifmo.se.api.service.components.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.dto.user.Message;
import ru.ifmo.se.api.common.dto.user.MessageType;
import ru.ifmo.se.api.common.dto.user.TokensDto;
import ru.ifmo.se.api.service.components.JwtComponent;
import ru.ifmo.se.api.service.components.MessageBuilder;
import ru.ifmo.se.api.service.config.RabbitMQConfig;
import ru.ifmo.se.api.service.services.UserService;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UserRefreshListener {
    private final UserService userService;
    private final MessageBuilder messageBuilder;
    private final JwtComponent jwtComponent;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.USER_REFRESH_QUEUE)
    public Message refresh(Message message) {
        try {
            TokensDto tokensRequest = objectMapper.convertValue(message.getPayload(), TokensDto.class);
            String refreshToken = tokensRequest.getRefreshToken();

            if (refreshToken == null || refreshToken.isEmpty()) throw new IllegalArgumentException("Refresh token is empty");
            if (!jwtComponent.verify(refreshToken)) throw new IllegalArgumentException("Invalid refresh token");

            Long userId = jwtComponent.getUserId(refreshToken);
            Instant creationTime = jwtComponent.getIssuedTime(refreshToken);
            if (userService.isUpdatedAfter(creationTime.plus(Duration.ofMinutes(1)), userId)) throw new IllegalArgumentException("Wrong refresh token");

            return messageBuilder.getMessage(userId);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }
}
