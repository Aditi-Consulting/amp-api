package com.alertsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullResult {
    @JsonProperty("execution_details")
    private ExecutionDetails executionDetails;

    @JsonProperty("task_agent_summary")
    private TaskAgentSummary taskAgentSummary;

    @JsonProperty("llm_analysis")
    private LlmAnalysis llmAnalysis;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExecutionDetails {
        @JsonProperty("user_input")
        private String userInput;

        @JsonProperty("alert_count")
        private Integer alertCount;

        @JsonProperty("resolutions_found")
        private Integer resolutionsFound;

        @JsonProperty("verification_status")
        private String verificationStatus;

        @JsonProperty("resolutions_executed")
        private Integer resolutionsExecuted;

        @JsonProperty("verification_message")
        private String verificationMessage;

        // New fields from the latest JSON structure
        private String evidence;

        @JsonProperty("root_cause")
        private String rootCause;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TaskAgentSummary {
        private String severity;

        @JsonProperty("issue_type")
        private String issueType;

        @JsonProperty("total_steps")
        private Integer totalSteps;

        @JsonProperty("completed_steps")
        private Integer completedSteps;

        @JsonProperty("workflow_status")
        private String workflowStatus;

        @JsonProperty("start_time")
        private String startTime;

        @JsonProperty("end_time")
        private String endTime;

        @JsonProperty("final_result")
        private String finalResult;

        @JsonProperty("workflow_type")
        private String workflowType;

        // New fields from the latest JSON structure
        private String evidence;

        @JsonProperty("root_cause")
        private String rootCause;

        @JsonProperty("failed_steps")
        private Integer failedSteps;

        @JsonProperty("llm_recommendation")
        private String llmRecommendation;

        @JsonProperty("verification_status")
        private String verificationStatus;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LlmAnalysis {
        private String evidence;

        private String severity;

        @JsonProperty("issue_type")
        private String issueType;

        @JsonProperty("root_cause")
        private String rootCause;

        @JsonProperty("llm_recommendation")
        private String llmRecommendation;

        @JsonProperty("verification_status")
        private String verificationStatus;
    }
}
