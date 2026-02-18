package com.alertsystem.controller;

import com.alertsystem.entity.Alert;
import com.alertsystem.entity.Resolution;
import com.alertsystem.service.AlertService;
import com.alertsystem.service.ResolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {
    private final AlertService alertService;
    private final ResolutionService resolutionService;

    @GetMapping
    public ResponseEntity<List<Alert>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts());
    }

    @PostMapping
    public ResponseEntity<Alert> addAlert(@RequestBody Alert alert) {
        Alert saved = alertService.addAlert(alert);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Alert> approveAlert(@PathVariable Long id) {
        Alert updated = alertService.updateStatus(id, "approved");
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Alert> rejectAlert(@PathVariable Long id) {
        Alert updated = alertService.updateStatus(id, "rejected");
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/resolution")
    public ResponseEntity<Resolution> getResolution(@PathVariable Long id) {
        Resolution resolution = resolutionService.getResolutionForAlert(id);
        return ResponseEntity.ok(resolution);
    }
}
