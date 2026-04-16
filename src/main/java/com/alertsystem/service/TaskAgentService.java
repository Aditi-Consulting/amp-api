package com.alertsystem.service;

import com.alertsystem.dto.response.ExecutionNodeResponse;
import com.alertsystem.dto.response.TaskAgentResponse;
import com.alertsystem.entity.TaskAgent;
import com.alertsystem.exception.ResourceNotFoundException;
import com.alertsystem.repository.TaskAgentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskAgentService {
    private final TaskAgentRepository taskAgentRepository;

    @Transactional
    public void updateConfidenceScore(Long id, Double score) {
        log.info("Updating confidence score for TaskAgent id={} to {}", id, score);
        TaskAgent taskAgent = taskAgentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task agent not found for id: " + id));
        taskAgent.setConfidenceScore(score);
        taskAgentRepository.save(taskAgent);
    }

    public TaskAgentResponse getTaskAgentByAlertId(Long alertId) {
        TaskAgent taskAgent = taskAgentRepository.findTopByTaskAgentAlertIdOrderByIdDesc(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Task agent not found for alert ID: " + alertId));

        return mapToResponse(taskAgent);
    }

    public List<TaskAgentResponse> getAllTaskAgentsByAlertId(Long alertId) {
        List<TaskAgent> taskAgents = taskAgentRepository.findByTaskAgentAlertIdOrderByIdDesc(alertId);

        if (taskAgents.isEmpty()) {
            throw new ResourceNotFoundException("No task agents found for alert ID: " + alertId);
        }

        return taskAgents.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TaskAgentResponse mapToResponse(TaskAgent taskAgent) {
        List<ExecutionNodeResponse> executionNodes = null;
        if (taskAgent.getTaskAgentExecutionNodes() != null) {
            executionNodes = taskAgent.getTaskAgentExecutionNodes().stream()
                    .map(node -> ExecutionNodeResponse.builder()
                            .status(node.getStatus())
                            .nodeName(node.getNodeName())
                            .fullResult(convertJsonNodeToString(node.getFullResult()))
                            .errorMessage(node.getErrorMessage())
                            .resultSummary(node.getResultSummary())
                            .executionOrder(node.getExecutionOrder())
                            .evidence(node.getEvidence())
                            .rootCause(node.getRootCause())
                            .verificationStatus(node.getVerificationStatus())
                            .build())
                    .collect(Collectors.toList());
        }

        // Handle execution_details JSON structure
        TaskAgentResponse.ExecutionDetailsResponse executionDetails = null;
        if (taskAgent.getTaskAgentFullResult() != null &&
            taskAgent.getTaskAgentFullResult().getExecutionDetails() != null) {
            var details = taskAgent.getTaskAgentFullResult().getExecutionDetails();
            executionDetails = TaskAgentResponse.ExecutionDetailsResponse.builder()
                    .userInput(details.getUserInput())
                    .alertCount(details.getAlertCount())
                    .resolutionsFound(details.getResolutionsFound())
                    .verificationStatus(details.getVerificationStatus())
                    .resolutionsExecuted(details.getResolutionsExecuted())
                    .verificationMessage(details.getVerificationMessage())
                    .evidence(details.getEvidence())
                    .rootCause(details.getRootCause())
                    .build();
        }

        // Handle task_agent_summary JSON structure
        TaskAgentResponse.TaskAgentSummaryResponse taskAgentSummary = null;
        if (taskAgent.getTaskAgentFullResult() != null &&
            taskAgent.getTaskAgentFullResult().getTaskAgentSummary() != null) {
            var summary = taskAgent.getTaskAgentFullResult().getTaskAgentSummary();
            taskAgentSummary = TaskAgentResponse.TaskAgentSummaryResponse.builder()
                    .severity(summary.getSeverity())
                    .issueType(summary.getIssueType())
                    .totalSteps(summary.getTotalSteps())
                    .completedSteps(summary.getCompletedSteps())
                    .workflowStatus(summary.getWorkflowStatus())
                    .startTime(summary.getStartTime())
                    .endTime(summary.getEndTime())
                    .finalResult(summary.getFinalResult())
                    .workflowType(summary.getWorkflowType())
                    .evidence(summary.getEvidence())
                    .rootCause(summary.getRootCause())
                    .failedSteps(summary.getFailedSteps())
                    .llmRecommendation(summary.getLlmRecommendation())
                    .verificationStatus(summary.getVerificationStatus())
                    .build();
        }

        // Handle llm_analysis JSON structure
        TaskAgentResponse.LlmAnalysisResponse llmAnalysis = null;
        if (taskAgent.getTaskAgentFullResult() != null &&
            taskAgent.getTaskAgentFullResult().getLlmAnalysis() != null) {
            var analysis = taskAgent.getTaskAgentFullResult().getLlmAnalysis();
            llmAnalysis = TaskAgentResponse.LlmAnalysisResponse.builder()
                    .evidence(analysis.getEvidence())
                    .severity(analysis.getSeverity())
                    .issueType(analysis.getIssueType())
                    .rootCause(analysis.getRootCause())
                    .llmRecommendation(analysis.getLlmRecommendation())
                    .verificationStatus(analysis.getVerificationStatus())
                    .build();
        }

        return TaskAgentResponse.builder()
                .id(taskAgent.getId())
                .alertId(taskAgent.getTaskAgentAlertId())
                .executionNodes(executionNodes)
                .executionDetails(executionDetails)
                .taskAgentSummary(taskAgentSummary)
                .llmAnalysis(llmAnalysis)
                .startTime(taskAgent.getTaskAgentStartTime())
                .endTime(taskAgent.getTaskAgentEndTime())
                .status(taskAgent.getTaskAgentStatus())
                .confidenceScore(taskAgent.getConfidenceScore())
                .totalNodes(taskAgent.getTaskAgentTotalNodes())
                .successfulNodes(taskAgent.getTaskAgentSuccessfulNodes())
                .failedNodes(taskAgent.getTaskAgentFailedNodes())
                .build();
    }

    private String convertJsonNodeToString(com.fasterxml.jackson.databind.JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }
        if (jsonNode.isTextual()) {
            return jsonNode.asText();
        }
        return jsonNode.toString();
    }
}
