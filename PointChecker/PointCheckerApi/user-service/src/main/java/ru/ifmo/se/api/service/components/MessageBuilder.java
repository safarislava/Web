package ru.ifmo.se.api.service.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.dto.user.Message;
import ru.ifmo.se.api.common.dto.user.MessageType;
import ru.ifmo.se.api.common.dto.user.TokensDto;
import ru.ifmo.se.api.service.models.User;
import ru.ifmo.se.api.service.services.UserService;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class MessageBuilder {
    private final UserService userService;
    private final JwtComponent jwtComponent;

    public Message getMessage(User user) {
        userService.update(user.getId());

        TokensDto tokensResponse = new TokensDto(
                jwtComponent.generate(user.getId(), Duration.ofMinutes(5)),
                jwtComponent.generate(user.getId(), Duration.ofDays(2))
        );
        return new Message(MessageType.SUCCESS_RESPONSE, tokensResponse);
    }

    public Message getMessage(Long userId) {
        return getMessage(userService.getUser(userId));
    }

    public Message getMessage(String username) {
        return getMessage(userService.getUser(username));
    }
}
