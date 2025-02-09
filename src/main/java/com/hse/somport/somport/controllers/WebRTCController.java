package com.hse.somport.somport.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Controller
@RequestMapping("/webrtc")
public class WebRTCController {

    @Autowired
    private WebSocketClient webSocketClient;

    private WebSocketSession session;

    @PostMapping("/connect")
    public String connectToWebRTC(@RequestBody String sdpOffer) {
        String serverUrl = "ws://localhost:8081/offer";  // URL для подключения к WebRTC серверу

        try {
            // Создаем WebSocket клиента для подключения к серверу WebRTC
            WebSocketHandler handler = new WebSocketHandler() {
                @Override
                public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                    System.out.println("WebSocket connection established");
                    // Отправляем SDP предложение серверу WebRTC
                    session.sendMessage(new TextMessage(sdpOffer));
                }


                @Override
                public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                    System.out.println("Received message: " + message.getPayload());
                    // Логика обработки полученного видеопотока
                }

                @Override
                public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                    System.err.println("WebSocket error: " + exception.getMessage());
                }


                @Override
                public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                    System.out.println("WebSocket connection closed");
                }

                @Override
                public boolean supportsPartialMessages() {
                    return false;
                }
            };

            webSocketClient = new StandardWebSocketClient();
            session = webSocketClient.doHandshake(handler, serverUrl).get();

            // Подключение прошло успешно
            return "Connected to WebRTC server at " + serverUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to connect to WebRTC server: " + e.getMessage();
        }
    }

    @PostMapping("/sendOffer")
    public String sendOffer(@RequestBody String sdpOffer) {
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(sdpOffer));
                return "SDP offer sent!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed to send SDP offer: " + e.getMessage();
            }
        } else {
            return "WebSocket session is not open!";
        }
    }
}

