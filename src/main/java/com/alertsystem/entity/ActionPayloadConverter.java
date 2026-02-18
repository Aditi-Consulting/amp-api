package com.alertsystem.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ActionPayloadConverter implements AttributeConverter<ActionPayload, String> {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public String convertToDatabaseColumn(ActionPayload attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert ActionPayload to JSON", e);
        }
    }

    @Override
    public ActionPayload convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ActionPayload.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to ActionPayload", e);
        }
    }
}
