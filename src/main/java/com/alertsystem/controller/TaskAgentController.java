package com.alertsystem.controller;

import com.alertsystem.dto.request.UpdateConfidenceScoreRequest;
import com.alertsystem.dto.response.TaskAgentResponse;
import com.alertsystem.service.TaskAgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-agent")
@RequiredArgsConstructor
@Tag(name = "Task Agent", description = "Task Agent execution details API")
public class TaskAgentController {

    private final TaskAgentService taskAgentService;

    @GetMapping("/{alertId}")
    @Operation(summary = "Get latest task agent execution by Alert ID")
    public ResponseEntity<TaskAgentResponse> getTaskAgentByAlertId(
            @Parameter(description = "Alert ID to fetch task agent execution for")
            @PathVariable Long alertId) {
        TaskAgentResponse response = taskAgentService.getTaskAgentByAlertId(alertId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{alertId}/all")
    @Operation(summary = "Get all task agent executions by Alert ID")
    public ResponseEntity<List<TaskAgentResponse>> getAllTaskAgentsByAlertId(
            @Parameter(description = "Alert ID to fetch all task agent executions for")
            @PathVariable Long alertId) {
        List<TaskAgentResponse> responses = taskAgentService.getAllTaskAgentsByAlertId(alertId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/confidence-score")
    @Operation(summary = "Update confidence score for a Task Agent by ID")
    public ResponseEntity<Void> updateConfidenceScore(
            @PathVariable Long id,
            @Valid @RequestBody UpdateConfidenceScoreRequest request) {
        taskAgentService.updateConfidenceScore(id, request.getConfidenceScore());
        return ResponseEntity.noContent().build();
    }

}
