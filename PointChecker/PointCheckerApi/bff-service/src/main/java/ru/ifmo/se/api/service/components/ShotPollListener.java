package ru.ifmo.se.api.service.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.ifmo.se.api.common.dto.shot.Message;
import ru.ifmo.se.api.common.dto.shot.MessageType;
import ru.ifmo.se.api.common.dto.shot.ShotResponse;
import ru.ifmo.se.api.service.config.RabbitMQConfig;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShotPollListener {
    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate template;

    @RabbitListener(queues = RabbitMQConfig.SHOT_POLL_RESPONSE_QUEUE)
    public void receiveShots(Message message) {
        try {
            if (message.getMessageType().equals(MessageType.ERROR_RESPONSE)) throw new IllegalArgumentException(message.getPayload().toString());
            if (message.getUserId() == null) throw new IllegalArgumentException("Invalid user id received");

            String userId = String.valueOf(message.getUserId());
            List<ShotResponse> shots = objectMapper.convertValue(message.getPayload(), new TypeReference<>() {});
            template.convertAndSendToUser(userId, "/queue/shots", shots);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
