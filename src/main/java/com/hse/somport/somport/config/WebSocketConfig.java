package com.hse.somport.somport.config;

import com.hse.somport.somport.handler.VideoProxyHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final VideoProxyHandler videoProxyHandler;

    public WebSocketConfig(VideoProxyHandler videoProxyHandler) {
        this.videoProxyHandler = videoProxyHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(videoProxyHandler, "/video-stream/{streamId}")
                .setAllowedOrigins("http://localhost:3000");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxBinaryMessageBufferSize(10_000_000); // 10MB
        container.setMaxTextMessageBufferSize(10_000_000); // 10MB (если нужно)
        container.setAsyncSendTimeout(0L); // Отключаем таймаут отправки
        //container.setIdleTimeout(60_000L); // 60 секунд бездействия
        return container;
    }
}
