package com.hse.somport.somport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class VideoConfigDto {
    private List<String> videoStreamList;
}
