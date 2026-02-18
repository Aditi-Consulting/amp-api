package com.alertsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionPayloadResponse {
    private List<String> parameters;
    private Boolean requiresApproval;
    private List<String> steps;
}
