package com.alertsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecutionNode {
    private String status;

    @JsonProperty("node_name")
    private String nodeName;

    @JsonProperty("full_result")
    private JsonNode fullResult;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("result_summary")
    private String resultSummary;

    @JsonProperty("execution_order")
    private Integer executionOrder;

    // New fields from the latest JSON structure
    private String evidence;

    @JsonProperty("root_cause")
    private String rootCause;

    @JsonProperty("verification_status")
    private String verificationStatus;
}
