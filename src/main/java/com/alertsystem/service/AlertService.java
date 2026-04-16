package com.alertsystem.service;

import com.alertsystem.entity.Alert;
import com.alertsystem.exception.AlertAlreadyExistsException;
import com.alertsystem.exception.ResourceNotFoundException;
import com.alertsystem.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final AlertRepository alertRepository;
    private final RestTemplate restTemplate = new RestTemplate();
//    private final ConcurrentLinkedQueue<Long> pendingAlerts = new ConcurrentLinkedQueue<>();

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @Transactional
    public Alert addAlert(Alert alert) {
        if (!StringUtils.hasText(alert.getTicketId()) ||
                !StringUtils.hasText(alert.getTicket()) ||
                !StringUtils.hasText(alert.getCreatedBy()) ||
                !StringUtils.hasText(alert.getSeverity())) {
            throw new IllegalArgumentException("Missing required alert fields");
        }

        Optional<Alert> existing = alertRepository.findByTicketId(alert.getTicketId());
        if (existing.isPresent()) {
            throw new AlertAlreadyExistsException("Alert already present");
        }

        if (!StringUtils.hasText(alert.getStatus())) {
            alert.setStatus("OPEN");
        }
        if (alert.getInsertedAt() == null) {
            alert.setInsertedAt(LocalDateTime.now());
        }

        //        pendingAlerts.add(savedAlert.getId());
        return alertRepository.save(alert);
    }

    // Scheduled to run every 5 seconds, triggers endpoint for alerts older than 20 seconds
//    @Scheduled(fixedRate = 5000)
//    public void processPendingAlerts() {
//        while (!pendingAlerts.isEmpty()) {
//            Long alertId = pendingAlerts.poll();
//            if (alertId != null) {
//                triggerExternalEndpoint(alertId);
//            }
//        }
//    }
//
//    private void triggerExternalEndpoint(Long alertId) {
//        String endpointUrl = "http://localhost:5000/invoke";
//        restTemplate.postForObject(endpointUrl, Map.of("alertId", alertId), String.class);
//    }

    public Alert updateStatus(Long id, String status) {
        Alert alert = alertRepository.findById(id).orElseThrow();
        alert.setStatus(status);
        alert.setProcessedAt(LocalDateTime.now());
        return alertRepository.save(alert);
    }

    @Transactional
    public Alert retryAlert(Long alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + alertId));
        
        alert.setStatus("RETRY_PENDING");

        return alertRepository.save(alert);
    }
}
