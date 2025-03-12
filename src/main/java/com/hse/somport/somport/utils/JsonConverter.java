package com.hse.somport.somport.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hse.somport.somport.dto.FeedConfigDetails;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Map;

@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<FeedConfigDetails, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(FeedConfigDetails attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON", e);
        }
    }

    @Override
    public FeedConfigDetails convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, FeedConfigDetails.class);
        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON", e);
        }
    }
}
