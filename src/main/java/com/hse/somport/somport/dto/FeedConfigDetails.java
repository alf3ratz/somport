package com.hse.somport.somport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedConfigDetails {

    @NotNull(message = "Поле 'feedCount' не может быть null")
    private Long feedCount;

    @NotNull(message = "Поле 'poolNumber' не может быть null")
    private Long poolNumber;
}
