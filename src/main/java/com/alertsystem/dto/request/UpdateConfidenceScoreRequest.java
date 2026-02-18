package com.alertsystem.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConfidenceScoreRequest {
    @NotNull(message = "confidenceScore is required")
    @DecimalMin(value = "15.0", inclusive = true, message = "confidenceScore must be >= 15")
    @DecimalMax(value = "100.0", inclusive = true, message = "confidenceScore must be <= 100")
    private Double confidenceScore;
}