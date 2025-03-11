package com.hse.somport.somport.service;

import com.hse.somport.somport.dto.VideoConfigDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoConfigService {

    public VideoConfigDto getStreamConfig() {
        return new VideoConfigDto(List.of("stream_1", "stream_2", "stream_3"));
    }
}
