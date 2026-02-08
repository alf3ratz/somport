package com.hse.somport.somport.controllers;

import com.hse.somport.somport.dto.FeedConfigDto;
import com.hse.somport.somport.dto.VideoConfigDto;
import com.hse.somport.somport.service.FeedConfigService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/feed-config/v1")
public class FeedConfigController {

    private final FeedConfigService feedConfigService;

    @GetMapping("/{id}")
    public FeedConfigDto getConfigById(@PathVariable Long id) {
        return feedConfigService.getConfigById(id);
    }

    @PostMapping
    public FeedConfigDto createConfig(@RequestBody FeedConfigDto feedConfigDto) {
        return feedConfigService.createConfig(feedConfigDto);
    }

    @PutMapping("/{id}")
    public FeedConfigDto updateConfigById(@RequestBody FeedConfigDto feedConfigDto, @PathVariable Long id) {
        return feedConfigService.updateConfigById(id, feedConfigDto);
    }

    @DeleteMapping("/{id}")
    public void deleteConfigById(@PathVariable Long id) {
        feedConfigService.deleteConfigById(id);
    }

    @GetMapping("/getAll")
    public List<FeedConfigDto> getAllConfigs() {
        return feedConfigService.getAllConfigs();
    }
}
