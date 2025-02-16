package com.hse.somport.somport.controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class VideoWebSocketController {

    private final SimpMessagingTemplate template;

    public VideoWebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/stream")
    public void handleStream(byte[] data) {
        // Перенаправляем данные от Python-сервера на WebSocket клиентов
        template.convertAndSend("/topic/video", data);
    }
}
