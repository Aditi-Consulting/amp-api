package com.alertsystem.dto.request;

import com.alertsystem.enums.ResolutionStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolutionRequest {
    @NotBlank(message = "Issue type is required")
    private String issueType;

    private String description;

    @NotBlank(message = "Action type is required")
    private String actionType;

    @Valid
    private ActionPayloadRequest actionPayload;

    @NotNull(message = "Status is required")
    private ResolutionStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
