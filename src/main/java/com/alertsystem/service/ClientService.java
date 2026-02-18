package com.alertsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    @Value("${external.service.classification.url:http://service:5000/invoke}")
    private String classificationServiceUrl;

    @Value("${external.service.resolution.url:http://task-agent:5001/trigger-agent}")
    private String resolutionServiceUrl;

    private final RestTemplate restTemplate;

    public ResponseEntity<String> triggerClassification(Long alertId) {
        try {
            log.info("Starting classification for alertId: {}", alertId);

            // Prepare request body
            Map<String, Long> requestBody = new HashMap<>();
            requestBody.put("alertId", alertId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Long>> entity = new HttpEntity<>(requestBody, headers);

            // Make synchronous call to external service
            ResponseEntity<String> response = restTemplate.postForEntity(
                classificationServiceUrl,
                entity,
                String.class
            );

            log.info("Classification service called successfully. Status: {}", response.getStatusCode());
            return response;

        } catch (Exception ex) {
            log.error("Error calling classification service: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error calling classification service: " + ex.getMessage());
        }
    }

    public ResponseEntity<String> triggerResolution(Long alertId) {
        try {
            log.info("Starting resolution for alertId: {}", alertId);

            // Prepare request body
            Map<String, Long> requestBody = new HashMap<>();
            requestBody.put("alertId", alertId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Long>> entity = new HttpEntity<>(requestBody, headers);

            log.info("[{}] Sending POST request with body: {}", "resolution", requestBody);

            // Make synchronous call to external service
            log.info("Before making the external call to resolution service");
            ResponseEntity<String> response = restTemplate.postForEntity(
                resolutionServiceUrl,
                entity,
                String.class
            );

            log.info("Resolution service called successfully. Status: {}", response.getStatusCode());
            return response;

        } catch (Exception ex) {
            log.error("Error calling resolution service: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error calling resolution service: " + ex.getMessage());
        }
    }
}
