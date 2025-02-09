//package com.hse.somport.somport.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hse.somport.somport.dto.WebRtcMessage;
//import org.springframework.web.socket.*;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class WebRtcSignalingHandler extends TextWebSocketHandler {
//
//    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) {
//        sessions.put(session.getId(), session);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
//        WebRtcMessage webRtcMessage = objectMapper.readValue(message.getPayload(), WebRtcMessage.class);
//
//        // Пересылаем сообщение всем другим клиентам
//        for (WebSocketSession s : sessions.values()) {
//            if (!s.getId().equals(session.getId())) {
//                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(webRtcMessage)));
//            }
//        }
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//        sessions.remove(session.getId());
//    }
//}