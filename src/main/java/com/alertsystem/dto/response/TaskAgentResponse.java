package com.alertsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAgentResponse {
    private Long id;
    private Long alertId;
    private List<ExecutionNodeResponse> executionNodes;
    private ExecutionDetailsResponse executionDetails;
    private TaskAgentSummaryResponse taskAgentSummary;
    private LlmAnalysisResponse llmAnalysis;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Double confidenceScore;
    private Integer totalNodes;
    private Integer successfulNodes;
    private Integer failedNodes;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExecutionDetailsResponse {
        private String userInput;
        private Integer alertCount;
        private Integer resolutionsFound;
        private String verificationStatus;
        private Integer resolutionsExecuted;
        private String verificationMessage;
        private String evidence;
        private String rootCause;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskAgentSummaryResponse {
        private String severity;
        private String issueType;
        private Integer totalSteps;
        private Integer completedSteps;
        private String workflowStatus;
        private String startTime;
        private String endTime;
        private String finalResult;
        private String workflowType;
        private String evidence;
        private String rootCause;
        private Integer failedSteps;
        private String llmRecommendation;
        private String verificationStatus;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LlmAnalysisResponse {
        private String evidence;
        private String severity;
        private String issueType;
        private String rootCause;
        private String llmRecommendation;
        private String verificationStatus;
    }
}
