package ru.ifmo.se.api.usersmodule.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import ru.ifmo.se.api.usersmodule.components.JwtComponent;
import ru.ifmo.se.api.usersmodule.config.RabbitMQConfig;
import ru.ifmo.se.api.usersmodule.dto.Message;
import ru.ifmo.se.api.usersmodule.dto.MessageType;
import ru.ifmo.se.api.usersmodule.dto.TokensDto;
import ru.ifmo.se.api.usersmodule.dto.UserDto;
import ru.ifmo.se.api.usersmodule.models.Role;
import ru.ifmo.se.api.usersmodule.models.User;
import ru.ifmo.se.api.usersmodule.services.UserRoleService;
import ru.ifmo.se.api.usersmodule.services.UserService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final JwtComponent jwtComponent;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTER_QUEUE)
    public Message register(Message message) {
        try {
            UserDto userDto = objectMapper.convertValue(message.getPayload(), UserDto.class);
            String username = userDto.getUsername();

            User user = userService.register(username, userDto.getPassword());
            userRoleService.addRole(user.getId(), Role.USER);

            return getMessage(user);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.USER_LOGIN_QUEUE)
    public Message login(Message message) {
        try {
            UserDto userDto = objectMapper.convertValue(message.getPayload(), UserDto.class);
            String username = userDto.getUsername();

            if (!userService.login(userDto.getUsername(), userDto.getPassword())) throw new IllegalArgumentException("Wrong username or password");

            return getMessage(userService.getUser(username));
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }

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

            return getMessage(userService.getUser(userId));
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.USER_LOGOUT_QUEUE)
    public Message logout(Message message) {
        try {
            TokensDto tokensRequest = objectMapper.convertValue(message.getPayload(), TokensDto.class);
            String accessToken = tokensRequest.getAccessToken();

            if (accessToken == null || accessToken.isEmpty()) throw new IllegalArgumentException("Access token is empty");
            if (!jwtComponent.verify(accessToken)) throw new IllegalArgumentException("Wrong refresh token");

            Long userId = jwtComponent.getUserId(accessToken);
            userService.update(userId);

            return new Message(MessageType.SUCCESS_RESPONSE, null);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.USER_GET_ROLES_QUEUE)
    public Message getRoles(Message message) {
        try {
            Long userId = objectMapper.convertValue(message.getPayload(), Long.class);

            List<String> roles = userRoleService.getRoles(userId).stream().map(Enum::toString).toList();
            return new Message(MessageType.SUCCESS_RESPONSE, roles);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }

    private Message getMessage(User  user) {
        userService.update(user.getId());

        TokensDto tokensResponse = new TokensDto(
                jwtComponent.generate(user.getId(), Duration.ofMinutes(5)),
                jwtComponent.generate(user.getId(), Duration.ofDays(2))
        );
        return new Message(MessageType.SUCCESS_RESPONSE, tokensResponse);
    }
}
