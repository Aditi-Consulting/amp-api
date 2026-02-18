package com.alertsystem.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Converter
@Slf4j
public class ExecutionNodesConverter implements AttributeConverter<List<ExecutionNode>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ExecutionNode> attribute) {
        try {
            return attribute == null ? null : objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            log.error("Error converting execution nodes to JSON: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Error converting execution nodes to JSON", e);
        }
    }

    @Override
    public List<ExecutionNode> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.trim().isEmpty()) {
                return null;
            }
            log.debug("Converting JSON to ExecutionNodes: {}", dbData);
            return objectMapper.readValue(dbData, new TypeReference<List<ExecutionNode>>() {});
        } catch (Exception e) {
            log.error("Error converting JSON to execution nodes. JSON data: {}, Error: {}", dbData, e.getMessage(), e);
            throw new IllegalArgumentException("Error converting JSON to execution nodes", e);
        }
    }
}
