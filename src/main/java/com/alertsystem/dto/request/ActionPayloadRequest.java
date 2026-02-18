package com.alertsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionPayloadRequest {
    @Builder.Default
    private List<String> parameters = new ArrayList<>();
    private Boolean requiresApproval;
    @Builder.Default
    private List<String> steps = new ArrayList<>();
}
