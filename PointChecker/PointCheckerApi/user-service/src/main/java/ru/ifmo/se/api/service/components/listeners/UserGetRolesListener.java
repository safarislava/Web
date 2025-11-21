package ru.ifmo.se.api.service.components.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.userservice.Message;
import ru.ifmo.se.api.common.userservice.MessageType;
import ru.ifmo.se.api.service.config.RabbitMQConfig;
import ru.ifmo.se.api.service.services.UserRoleService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserGetRolesListener {
    private final ObjectMapper objectMapper;
    private final UserRoleService userRoleService;

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
}
