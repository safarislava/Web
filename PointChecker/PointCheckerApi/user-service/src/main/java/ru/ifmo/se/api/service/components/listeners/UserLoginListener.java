package ru.ifmo.se.api.service.components.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.userservice.Message;
import ru.ifmo.se.api.common.userservice.MessageType;
import ru.ifmo.se.api.common.userservice.UserDto;
import ru.ifmo.se.api.service.components.MessageBuilder;
import ru.ifmo.se.api.service.config.RabbitMQConfig;
import ru.ifmo.se.api.service.services.UserService;

@Component
@RequiredArgsConstructor
public class UserLoginListener {
    private final UserService userService;
    private final MessageBuilder messageBuilder;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.USER_LOGIN_QUEUE)
    public Message login(Message message) {
        try {
            UserDto userDto = objectMapper.convertValue(message.getPayload(), UserDto.class);
            String username = userDto.getUsername();

            if (!userService.login(userDto.getUsername(), userDto.getPassword())) throw new IllegalArgumentException("Wrong username or password");

            return messageBuilder.getMessage(username);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }
}
