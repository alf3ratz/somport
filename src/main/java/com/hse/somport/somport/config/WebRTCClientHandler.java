package com.hse.somport.somport.config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class WebRTCClientHandler {

    private WebSocketSession session;

    public WebRTCClientHandler(WebSocketSession session) {
        this.session = session;
    }

    public void sendSDPOffer(String sdpOffer) throws Exception {
        // Отправка SDP предложения через WebSocket
        session.sendMessage(new TextMessage("{\"sdp\": \"" + sdpOffer + "\", \"type\": \"offer\"}"));
    }

    public void sendICECandidate(String candidate) throws Exception {
        // Отправка ICE кандидатов через WebSocket
        session.sendMessage(new TextMessage("{\"candidate\": \"" + candidate + "\"}"));
    }
}
