package com.hse.somport.somport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.*;

@Configuration
//@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Bean
    public WebSocketClient webSocketClient() {
        // Возвращаем новый экземпляр WebSocketClient
        return new StandardWebSocketClient();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

    }
//    private final WebSocketHandlerImpl webSocketHandler;
//
//    public WebSocketConfig(WebSocketHandlerImpl webSocketHandler) {
//        this.webSocketHandler = webSocketHandler;
//    }
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler, "/offer")
//                .setAllowedOrigins("*");
//    }
}
