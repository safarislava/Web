package ru.ifmo.se.api.service.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.ifmo.se.api.common.dto.user.Message;
import ru.ifmo.se.api.common.dto.user.MessageType;
import ru.ifmo.se.api.common.dto.user.TokensDto;
import ru.ifmo.se.api.common.dto.user.UserDto;
import ru.ifmo.se.api.service.config.RabbitMQConfig;
import ru.ifmo.se.api.service.exceptions.UnAuthenticationException;
import ru.ifmo.se.api.service.exceptions.UnAuthorizationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMessageService {
    private final RabbitTemplate template;
    private final ObjectMapper objectMapper;

    public TokensDto sendRegisterUserRequest(UserDto userDto) {
        Message messageRequest = new Message(MessageType.REGISTER_REQUEST, userDto);

        Message messageResponse = template.convertSendAndReceiveAsType(
                RabbitMQConfig.USER_REQUEST_EXCHANGE,
                RabbitMQConfig.USER_REGISTER_ROUTING_KEY,
                messageRequest,
                new ParameterizedTypeReference<>() {}
        );

        if (messageResponse == null)
            throw new IllegalArgumentException("Invalid message received");
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE))
            throw new UnAuthenticationException(messageResponse.getPayload().toString());
        return objectMapper.convertValue(messageResponse, TokensDto.class);
    }

    public TokensDto sendLoginUserRequest(UserDto userDto) {
        Message messageRequest = new Message(MessageType.LOGIN_REQUEST, userDto);

        Message messageResponse = template.convertSendAndReceiveAsType(
                RabbitMQConfig.USER_REQUEST_EXCHANGE,
                RabbitMQConfig.USER_LOGIN_ROUTING_KEY,
                messageRequest,
                new ParameterizedTypeReference<>() {}
        );

        if (messageResponse == null)
            throw new IllegalArgumentException("Invalid message received");
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE))
            throw new UnAuthenticationException(messageResponse.getPayload().toString());
        return objectMapper.convertValue(messageResponse.getPayload(), TokensDto.class);
    }

    public TokensDto sendSyncRequest(String username) {
        Message messageRequest = new Message(MessageType.SYNC_REQUEST, username);

        Message messageResponse = template.convertSendAndReceiveAsType(
                RabbitMQConfig.USER_REQUEST_EXCHANGE,
                RabbitMQConfig.USER_SYNC_ROUTING_KEY,
                messageRequest,
                new ParameterizedTypeReference<>() {}
        );

        if (messageResponse == null)
            throw new IllegalArgumentException("Invalid message received");
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE))
            throw new UnAuthenticationException(messageResponse.getPayload().toString());
        return objectMapper.convertValue(messageResponse.getPayload(), TokensDto.class);
    }

    public TokensDto sendRefreshUserRequest(TokensDto tokensDto) {
        Message messageRequest = new Message(MessageType.REFRESH_REQUEST, tokensDto);

        Message messageResponse = template.convertSendAndReceiveAsType(
                RabbitMQConfig.USER_REQUEST_EXCHANGE,
                RabbitMQConfig.USER_REFRESH_ROUTING_KEY,
                messageRequest,
                new ParameterizedTypeReference<>() {}
        );

        if (messageResponse == null)
            throw new IllegalArgumentException("Invalid message received");
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE))
            throw new UnAuthorizationException(messageResponse.getPayload().toString());
        return objectMapper.convertValue(messageResponse.getPayload(), TokensDto.class);
    }

    public void sendLogoutUserRequest(TokensDto tokensDto) {
        Message messageRequest = new Message(MessageType.LOGOUT_REQUEST, tokensDto);

        Message messageResponse = template.convertSendAndReceiveAsType(
                RabbitMQConfig.USER_REQUEST_EXCHANGE,
                RabbitMQConfig.USER_LOGOUT_ROUTING_KEY,
                messageRequest,
                new ParameterizedTypeReference<>() {}
        );

        if (messageResponse == null)
            throw new IllegalArgumentException("Invalid message received");
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE))
            throw new UnAuthenticationException(messageResponse.getPayload().toString());
    }

    public List<String> sendGetRolesRequest(Long userId) {
        Message messageRequest = new Message(MessageType.GET_ROLES_REQUEST, userId);

        Message messageResponse = template.convertSendAndReceiveAsType(
                RabbitMQConfig.USER_REQUEST_EXCHANGE,
                RabbitMQConfig.USER_GET_ROLES_ROUTING_KEY,
                messageRequest,
                new ParameterizedTypeReference<>() {}
        );

        if (messageResponse == null)
            throw new IllegalArgumentException("Invalid message received");
        if (messageResponse.getMessageType().equals(MessageType.ERROR_RESPONSE))
            throw new UnAuthorizationException(messageResponse.getPayload().toString());
        return objectMapper.convertValue(messageResponse.getPayload(), new TypeReference<>() {});
    }
}
