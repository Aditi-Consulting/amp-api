package com.alertsystem.controller;


import com.alertsystem.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
