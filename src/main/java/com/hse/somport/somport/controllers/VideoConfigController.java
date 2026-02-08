package com.hse.somport.somport.controllers;

import com.hse.somport.somport.dto.VideoConfigDto;
import com.hse.somport.somport.service.VideoConfigService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/video-config")
public class VideoConfigController {

    private final VideoConfigService videoStreamService;

    @GetMapping("/streams")
    public VideoConfigDto register() {
        return videoStreamService.getStreamConfig();
    }
}
