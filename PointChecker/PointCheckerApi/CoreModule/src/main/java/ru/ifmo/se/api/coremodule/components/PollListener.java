package ru.ifmo.se.api.coremodule.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import ru.ifmo.se.api.coremodule.config.RabbitMQConfig;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.Message;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.MessageType;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.ShotResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class PollListener {
    private final Map<Long, Set<DeferredResult<ResponseEntity<List<ShotResponse>>>>> pollListeners = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitMQConfig.SHOT_POLL_RESPONSE_QUEUE)
    public void receiveShots(Message message) {
        try {
            if (message.getMessageType().equals(MessageType.ERROR_RESPONSE)) throw new IllegalArgumentException(message.getPayload().toString());
            if (message.getUserId() == null) throw new IllegalArgumentException("Invalid user id received");

            var userListeners = pollListeners.get(message.getUserId());
            if (userListeners == null) return;

            List<ShotResponse> shots = objectMapper.convertValue(message.getPayload(), new TypeReference<>() {});
            ResponseEntity<List<ShotResponse>> responseEntity = ResponseEntity.ok(shots);
            for (var poll : userListeners) {
                poll.setResult(responseEntity);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addListener(Long userId, DeferredResult<ResponseEntity<List<ShotResponse>>> pollListener) {
        var userListeners = pollListeners.computeIfAbsent(userId, key -> new CopyOnWriteArraySet<>());
        userListeners.add(pollListener);
    }

    public void removeListener(Long userId, DeferredResult<ResponseEntity<List<ShotResponse>>> pollListener) {
        var userListeners = pollListeners.get(userId);
        if (userListeners != null) {
            pollListeners.get(userId).remove(pollListener);
            if (userListeners.isEmpty()) {
                pollListeners.remove(userId);
            }
        }
    }
}
