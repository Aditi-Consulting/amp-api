package com.alertsystem.controller;


import com.alertsystem.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Client", description = "Client endpoints for UI interactions")
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/trigger-classification")
    @Operation(summary = "Trigger classification and wait for response")
    public ResponseEntity<String> triggerClassification(@RequestParam("alertId") Long alertId) {

        log.info("Received request to trigger classification for alertId: {}", alertId);

        ResponseEntity<String> response = clientService.triggerClassification(alertId);

        log.info("Classification completed with status: {}", response.getStatusCode());

        return response;
    }

    @PostMapping("/trigger-task-agent")
    @Operation(summary = "Trigger resolution and wait for response")
    public ResponseEntity<String> triggerResolution(@RequestParam("alertId") Long alertId) {

        log.info("Received request to trigger resolution for alertId: {}", alertId);

        ResponseEntity<String> response = clientService.triggerResolution(alertId);

        log.info("Resolution completed with status: {}", response.getStatusCode());

        return response;
    }

    @PostMapping("/trigger-agent-by-source")
    @Operation(summary = "Trigger appropriate agent based on alert source")
    public ResponseEntity<Map<String, Object>> triggerAgentBySource(@RequestParam("alertId") Long alertId) {
        log.info("Received request to trigger agent by source for alertId: {}", alertId);
        
        ResponseEntity<String> agentResponse = clientService.triggerAgentBySource(alertId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("alertId", alertId);
        response.put("agentStatus", agentResponse.getStatusCode().value());
        response.put("agentResponse", agentResponse.getBody());
        
        return ResponseEntity.status(agentResponse.getStatusCode()).body(response);
    }
}
