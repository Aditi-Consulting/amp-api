package com.alertsystem.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class FullResultConverter implements AttributeConverter<FullResult, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(FullResult attribute) {
        try {
            return attribute == null ? null : objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            log.error("Error converting FullResult to JSON: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Error converting full result to JSON", e);
        }
    }

    @Override
    public FullResult convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.trim().isEmpty()) {
                return null;
            }
            log.debug("Converting JSON to FullResult: {}", dbData);
            return objectMapper.readValue(dbData, FullResult.class);
        } catch (Exception e) {
            log.error("Error converting JSON to FullResult. JSON data: {}, Error: {}", dbData, e.getMessage(), e);
            throw new IllegalArgumentException("Error converting JSON to full result", e);
        }
    }
}
