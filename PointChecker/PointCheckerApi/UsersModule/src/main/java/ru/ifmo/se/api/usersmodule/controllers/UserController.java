package ru.ifmo.se.api.usersmodule.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.ifmo.se.api.usersmodule.components.JwtComponent;
import ru.ifmo.se.api.usersmodule.config.RabbitMQConfig;
import ru.ifmo.se.api.usersmodule.dto.Message;
import ru.ifmo.se.api.usersmodule.dto.MessageType;
import ru.ifmo.se.api.usersmodule.dto.TokensDto;
import ru.ifmo.se.api.usersmodule.dto.UserDto;
import ru.ifmo.se.api.usersmodule.models.User;
import ru.ifmo.se.api.usersmodule.services.UserService;

import java.time.Duration;
import java.time.Instant;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtComponent jwtComponent;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTER_QUEUE)
    @SendTo
    public Message register(Message message) {
        try {
            UserDto userDto = objectMapper.convertValue(message.getPayload(), UserDto.class);
            String username = userDto.getUsername();

            userService.register(username, userDto.getPassword());
            userService.update(username);
            User user = userService.getUser(username);

            TokensDto tokensResponse = new TokensDto(
                    jwtComponent.generate(user.getId(), Duration.ofMinutes(5)),
                    jwtComponent.generate(user.getId(), Duration.ofDays(2))
            );
            return new Message(MessageType.SUCCESS_RESPONSE, tokensResponse);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.USER_LOGIN_QUEUE)
    @SendTo
    public Message login(Message message) {
        try {
            UserDto userDto = objectMapper.convertValue(message.getPayload(), UserDto.class);
            String username = userDto.getUsername();

            if (!userService.login(userDto.getUsername(), userDto.getPassword())) throw new IllegalArgumentException("Wrong username or password");

            User user = userService.getUser(username);
            userService.update(userDto.getUsername());

            TokensDto tokensResponse = new TokensDto(
                    jwtComponent.generate(user.getId(), Duration.ofMinutes(5)),
                    jwtComponent.generate(user.getId(), Duration.ofDays(2))
            );
            return new Message(MessageType.SUCCESS_RESPONSE, tokensResponse);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.USER_REFRESH_QUEUE)
    @SendTo
    public Message refresh(Message message) {
        try {
            TokensDto tokensRequest = objectMapper.convertValue(message.getPayload(), TokensDto.class);
            String refreshToken = tokensRequest.getRefreshToken();

            if (refreshToken == null || refreshToken.isEmpty()) throw new IllegalArgumentException("Refresh token is empty");
            if (!jwtComponent.verify(refreshToken)) throw new IllegalArgumentException("Invalid refresh token");

            String username = jwtComponent.getUsername(refreshToken);
            Instant creationTime = jwtComponent.getIssuedTime(refreshToken);
            if (userService.isUpdatedAfter(creationTime.plus(Duration.ofMinutes(1)), username)) throw new IllegalArgumentException("Wrong refresh token");

            User user = userService.getUser(username);
            userService.update(username);

            TokensDto tokensResponse = new TokensDto(
                    jwtComponent.generate(user.getId(), Duration.ofMinutes(5)),
                    jwtComponent.generate(user.getId(), Duration.ofDays(2))
            );
            return new Message(MessageType.SUCCESS_RESPONSE, tokensResponse);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.USER_LOGOUT_QUEUE)
    @SendTo
    public Message logout(Message message) {
        try {
            TokensDto tokensRequest = objectMapper.convertValue(message.getPayload(), TokensDto.class);
            String accessToken = tokensRequest.getAccessToken();

            if (accessToken == null || accessToken.isEmpty()) throw new IllegalArgumentException("Access token is empty");
            if (!jwtComponent.verify(accessToken)) throw new IllegalArgumentException("Wrong refresh token");

            String username = jwtComponent.getUsername(accessToken);
            userService.update(username);

            return new Message(MessageType.SUCCESS_RESPONSE, null);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }
}
