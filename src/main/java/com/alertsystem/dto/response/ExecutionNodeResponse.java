package com.alertsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionNodeResponse {
    private String status;
    private String nodeName;
    private String fullResult;
    private String errorMessage;
    private String resultSummary;
    private Integer executionOrder;

    // New fields for the evolved JSON structure
    private String evidence;
    private String rootCause;
    private String verificationStatus;
}
