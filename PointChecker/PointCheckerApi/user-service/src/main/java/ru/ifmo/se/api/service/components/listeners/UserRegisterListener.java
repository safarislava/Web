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
import ru.ifmo.se.api.service.models.Role;
import ru.ifmo.se.api.service.models.User;
import ru.ifmo.se.api.service.services.UserRoleService;
import ru.ifmo.se.api.service.services.UserService;

@Component
@RequiredArgsConstructor
public class UserRegisterListener {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final MessageBuilder messageBuilder;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTER_QUEUE)
    public Message register(Message message) {
        try {
            UserDto userDto = objectMapper.convertValue(message.getPayload(), UserDto.class);
            String username = userDto.getUsername();

            User user = userService.register(username, userDto.getPassword());
            userRoleService.addRole(user.getId(), Role.USER);

            return messageBuilder.getMessage(user);
        }
        catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }
}
