package com.hse.somport.somport.config;

import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandlerImpl extends TextWebSocketHandler {

    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        System.out.println("WebSocket connection established");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Получаем сообщение (SDP или ICE кандидат)
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        // Обработка сообщений
        if (payload.contains("sdp")) {
            handleSDP(payload);
        } else if (payload.contains("candidate")) {
            handleICECandidate(payload);
        }
    }

    private void handleSDP(String sdp) {
        // Логика обработки SDP предложения
        System.out.println("Handling SDP: " + sdp);
    }

    private void handleICECandidate(String candidate) {
        // Логика обработки ICE кандидатов
        System.out.println("Handling ICE Candidate: " + candidate);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus closeStatus) throws Exception {
        System.out.println("WebSocket connection closed");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }
}
