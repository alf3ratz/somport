package com.hse.somport.somport.controllers;

import com.hse.somport.somport.service.HlsStreamService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hls")
public class HlsStreamController {

    private final HlsStreamService hlsStreamService;

    public HlsStreamController(HlsStreamService hlsStreamService) {
        this.hlsStreamService = hlsStreamService;
    }

    // 📌 Получение HLS-плейлиста (m3u8)
    @GetMapping("/playlist")
    public Mono<ResponseEntity<byte[]>> getPlaylist() {
        return hlsStreamService.getHlsPlaylist()
                .map(playlist -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "application/vnd.apple.mpegurl")
                        .body(playlist.getBytes()))  // 🔹 Преобразуем в byte[]
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 📌 Получение HLS-сегмента (TS-файл)
    @GetMapping("/segment/{fileName}")
    public Mono<ResponseEntity<byte[]>> getSegment(@PathVariable String fileName) {
        return hlsStreamService.getHlsSegment(fileName)
                .map(segment -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp2t")
                        .body(segment))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/video", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getVideoPlayer() {
        String html = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>HLS Video Player</title>
            </head>
            <body>
                <h2>HLS Video Stream</h2>
                <video id="video" width="640" height="360" controls>
                    <source src="http://localhost:8081/stream.m3u8" type="application/vnd.apple.mpegurl">
                    Your browser does not support HLS streaming.
                </video>
            </body>
            </html>
        """;
        return ResponseEntity.ok().body(html);
    }
}

