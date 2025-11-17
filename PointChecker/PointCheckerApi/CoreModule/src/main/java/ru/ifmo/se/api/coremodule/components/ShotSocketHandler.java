package ru.ifmo.se.api.coremodule.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.ifmo.se.api.coremodule.dto.shotsmodule.ShotResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@RequiredArgsConstructor
public class ShotSocketHandler extends TextWebSocketHandler {
    private final Map<Long, Set<WebSocketSession>> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId != null) {
            sessions.compute(userId, (key, existingSessions) -> {
                if (existingSessions == null) {
                    return new CopyOnWriteArraySet<>(Set.of(session));
                } else {
                    existingSessions.add(session);
                    return existingSessions;
                }
            });
        } else {
            session.close(CloseStatus.POLICY_VIOLATION.withReason("User ID not found in session"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId != null) {
            Set<WebSocketSession> userSessions = sessions.get(userId);
            if (userSessions != null) {
                userSessions.remove(session);
                if (userSessions.isEmpty()) {
                    sessions.remove(userId);
                }
            }
        }
    }

    public void sendShotsToUser(Long userId, List<ShotResponse> shots) {
        Set<WebSocketSession> userSessions = sessions.get(userId);
        if (userSessions == null) return;

        var iterator = userSessions.iterator();
        while (iterator.hasNext()) {
            WebSocketSession session = iterator.next();
            if (session.isOpen()) {
                try {
                    String message = objectMapper.writeValueAsString(shots);
                    session.sendMessage(new TextMessage(message));
                } catch (IOException ignored) {
                    iterator.remove();
                }
            } else {
                iterator.remove();
            }
        }

        if (userSessions.isEmpty()) {
            sessions.remove(userId);
        }
    }
}
