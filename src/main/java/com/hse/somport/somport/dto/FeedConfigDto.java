package com.hse.somport.somport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
@Getter
@Setter
public class FeedConfigDto {

    @JsonProperty("config")
    private FeedConfigDetails config;
}
