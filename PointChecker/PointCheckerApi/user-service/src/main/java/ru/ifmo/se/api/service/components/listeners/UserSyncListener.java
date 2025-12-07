package ru.ifmo.se.api.service.components.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.dto.user.Message;
import ru.ifmo.se.api.common.dto.user.MessageType;
import ru.ifmo.se.api.service.components.MessageBuilder;
import ru.ifmo.se.api.service.config.RabbitMQConfig;
import ru.ifmo.se.api.service.models.Role;
import ru.ifmo.se.api.service.models.User;
import ru.ifmo.se.api.service.services.UserRoleService;
import ru.ifmo.se.api.service.services.UserService;

@Component
@RequiredArgsConstructor
public class UserSyncListener {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ObjectMapper objectMapper;
    private final MessageBuilder messageBuilder;

    @RabbitListener(queues = RabbitMQConfig.USER_SYNC_QUEUE)
    public Message sync(Message message) {
        try {
            String username = objectMapper.convertValue(message.getPayload(), String.class);

            try {
                User user = userService.getUser(username);
                return messageBuilder.getMessage(user);
            }
            catch (Exception e) {
                User user = userService.register(username);
                userRoleService.addRole(user.getId(), Role.USER);
                return messageBuilder.getMessage(user);
            }
        } catch (Exception e) {
            return new Message(MessageType.ERROR_RESPONSE, e.getMessage());
        }
    }
}
