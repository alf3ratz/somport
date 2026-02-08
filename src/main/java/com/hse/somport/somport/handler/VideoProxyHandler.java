package com.hse.somport.somport.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class VideoProxyHandler extends BinaryWebSocketHandler {

    private final Map<String, Set<WebSocketSession>> streamSessions =
            new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        var streamId = extractStreamId(session);
        if (streamId == null) {
            log.warn("Wrong websocket URL: {}", session.getUri());
            try {
                session.close();
            } catch (IOException e) {
            }
            return;
        }

        // Добавляем сессию в соответствующую группу
        streamSessions.computeIfAbsent(streamId,
                k -> ConcurrentHashMap.newKeySet()).add(session);

        session.getAttributes().put("streamId", streamId);
        System.out.println("✅ Клиент подключился к " + streamId + ": " +
                session.getRemoteAddress());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        var streamId = (String) session.getAttributes().get("streamId");
        if (streamId != null) {
            streamSessions.getOrDefault(streamId, Collections.emptySet())
                    .remove(session);
        }
        System.out.println("❌ Клиент отключился от " + streamId + ": " +
                session.getRemoteAddress());
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        var streamId = (String) session.getAttributes().get("streamId");
        if (streamId == null) return;

        Set<WebSocketSession> sessions = streamSessions.get(streamId);
        if (sessions == null || sessions.isEmpty()) return;

        ByteBuffer buf = message.getPayload();
        byte[] payload = new byte[buf.remaining()];
        buf.get(payload);

        sessions.parallelStream()
                .filter(WebSocketSession::isOpen)
                .forEach(client -> {
                    try {
                        client.sendMessage(new BinaryMessage(payload));
                    } catch (IOException e) {
                        System.err.println("Ошибка отправки клиенту " +
                                client.getRemoteAddress() + ": " + e.getMessage());
                        sessions.remove(client);
                    }
                });
    }

    private String extractStreamId(WebSocketSession session) {
        String path = Objects.requireNonNull(session.getUri()).getPath();
        String[] parts = path.split("/");
        return (parts.length > 2) ? parts[2] : null;
    }
}