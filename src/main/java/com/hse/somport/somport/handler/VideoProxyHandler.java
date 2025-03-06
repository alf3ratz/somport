package com.hse.somport.somport.handler;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VideoProxyHandler extends BinaryWebSocketHandler {

    private final Set<WebSocketSession> frontendSessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("✅ Клиент подключился: " + session.getRemoteAddress());
        frontendSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("❌ Клиент  отключился: " + session.getRemoteAddress());
        frontendSessions.remove(session);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        for (WebSocketSession frontendSession : frontendSessions) {
            if (frontendSession.isOpen()) {
                try {
                    frontendSession.sendMessage(message);
                } catch (Exception e) {
                    System.err.println("Ошибка отправки кадра: " + e.getMessage());
                }
            }
        }
    }
}