package com.hse.somport.somport.controllers;

import com.hse.somport.somport.dto.VideoConfigDto;
import com.hse.somport.somport.service.VideoConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video-config")
public class VideoConfigController {
    @Autowired
    private VideoConfigService videoStreamService;

    @PostMapping("/streams")
    public VideoConfigDto register() {
        return videoStreamService.getStreamConfig();
    }
}
