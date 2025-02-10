package com.hse.somport.somport.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HlsStreamService {

    private final WebClient webClient;

    public HlsStreamService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    // Получение HLS плейлиста
    public Mono<String> getHlsPlaylist() {
        return webClient.get()
                .uri("/stream.m3u8")  // Путь к HLS-плейлисту
                .retrieve()
                .bodyToMono(String.class);
    }

    // Получение HLS-сегмента
    public Mono<byte[]> getHlsSegment(String segment) {
        return webClient.get()
                .uri("/" + segment)
                .retrieve()
                .bodyToMono(byte[].class);
    }
}
