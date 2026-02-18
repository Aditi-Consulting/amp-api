package com.alertsystem.dto.response;

import com.alertsystem.enums.ResolutionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolutionResponse {
    private Long id;
    private String issueType;
    private String description;
    private String actionType;
    private ActionPayloadResponse actionPayload;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ResolutionStatus status;
}
