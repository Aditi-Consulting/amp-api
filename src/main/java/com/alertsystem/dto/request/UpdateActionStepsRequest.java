package com.alertsystem.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateActionStepsRequest {
    @NotNull(message = "Action steps are required")
    @NotEmpty(message = "Action steps cannot be empty")
    private List<String> steps;
}

