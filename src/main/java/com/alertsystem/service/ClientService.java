package com.alertsystem.service;

import com.alertsystem.entity.Alert;
import com.alertsystem.exception.ResourceNotFoundException;
import com.alertsystem.repository.AlertRepository;
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

    @Value("${external.service.agents.kubernetes.url:http://task-agent:5001/trigger-agent}")
    private String kubernetesAgentUrl;

    @Value("${external.service.agents.splunk.url:http://splunk-agent:5004/api/v1/splunk-agent}")
    private String splunkAgentUrl;

    @Value("${external.service.agents.servicenow.url:http://device-agent:8888/api/v1/unlock}")
    private String serviceNowAgentUrl;

    private final RestTemplate restTemplate;
    private final AlertRepository alertRepository;

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

    /**
     * Trigger appropriate agent based on alert source
     */
    public ResponseEntity<String> triggerAgentBySource(Long alertId) {
        try {
            log.info("Triggering agent based on source for alertId: {}", alertId);

            Alert alert = alertRepository.findById(alertId)
                    .orElseThrow(() -> new ResourceNotFoundException("Alert not found: " + alertId));

            String source = alert.getSource();
            log.info("Alert {} has source: '{}'", alertId, source);

            String targetUrl = resolveAgentUrl(source);
            
            // Prepare request body
            Map<String, Long> requestBody = new HashMap<>();
            requestBody.put("alertId", alertId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Long>> entity = new HttpEntity<>(requestBody, headers);
            
            log.info("Sending POST request to {} with body: {}", targetUrl, requestBody);

            ResponseEntity<String> response = restTemplate.postForEntity(
                targetUrl,
                entity,
                String.class
            );

            log.info("Agent called successfully. Status: {}", response.getStatusCode());
            return response;

        } catch (Exception ex) {
            log.error("Error calling agent for alertId {}: {}", alertId, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error calling agent: " + ex.getMessage());
        }
    }

    /**
     * Resolve agent URL based on alert source
     * Handles sources like: "Kubernetes", "Splunk", "Service Now" (with space)
     */
    private String resolveAgentUrl(String source) {
        if (source == null || source.isBlank()) {
            log.warn("Alert source is null or empty. Using default Kubernetes agent.");
            return kubernetesAgentUrl;
        }

        // Normalize source: trim, lowercase, remove extra spaces
        String normalizedSource = source.trim().toLowerCase().replaceAll("\\s+", "");

        if (normalizedSource.contains("kubernetes") || normalizedSource.equals("k8s")) {
            log.info("Routing to Kubernetes agent for source: '{}'", source);
            return kubernetesAgentUrl;
        } else if (normalizedSource.contains("splunk")) {
            log.info("Routing to Splunk agent for source: '{}'", source);
            return splunkAgentUrl;
        } else if (normalizedSource.contains("servicenow") || normalizedSource.contains("service")) {
            log.info("Routing to ServiceNow agent for source: '{}'", source);
            return serviceNowAgentUrl;
        } else {
            log.warn("Unknown source: '{}'. Using default Kubernetes agent.", source);
            return kubernetesAgentUrl;
        }
    }
}
