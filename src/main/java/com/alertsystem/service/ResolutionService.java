package com.alertsystem.service;

import com.alertsystem.dto.request.ResolutionRequest;
import com.alertsystem.dto.response.ResolutionResponse;
import com.alertsystem.entity.Resolution;
import com.alertsystem.exception.ResourceNotFoundException;
import com.alertsystem.repository.ResolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ResolutionService {
    private final ResolutionRepository resolutionRepository;

    public List<ResolutionResponse> getAllResolutions() {
        return resolutionRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public ResolutionResponse getResolutionById(Long id) {
        Resolution resolution = resolutionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Resolution", "id", id));
        return mapToResponse(resolution);
    }

    public ResolutionResponse createResolution(ResolutionRequest request) {
        Resolution resolution = Resolution.builder()
            .issueType(request.getIssueType())
            .description(request.getDescription())
            .actionType(request.getActionType())
            .status(request.getStatus())
            .actionSteps(request.getActionPayload() != null ?
                com.alertsystem.entity.ActionPayload.builder()
                    .parameters(request.getActionPayload().getParameters())
                    .requiresApproval(request.getActionPayload().getRequiresApproval())
                    .steps(request.getActionPayload().getSteps())
                    .build() : null)
            .createdAt(request.getCreatedAt())
            .updatedAt(request.getUpdatedAt())
            .build();
        Resolution saved = resolutionRepository.save(resolution);
        return mapToResponse(saved);
    }

    private ResolutionResponse mapToResponse(Resolution resolution) {
        return ResolutionResponse.builder()
            .id(resolution.getId())
            .issueType(resolution.getIssueType())
            .description(resolution.getDescription())
            .actionType(resolution.getActionType())
            .actionPayload(resolution.getActionSteps() != null ?
                com.alertsystem.dto.response.ActionPayloadResponse.builder()
                    .parameters(resolution.getActionSteps().getParameters())
                    .requiresApproval(resolution.getActionSteps().getRequiresApproval())
                    .steps(resolution.getActionSteps().getSteps())
                    .build() : null)
            .createdAt(resolution.getCreatedAt())
            .updatedAt(resolution.getUpdatedAt())
            .status(resolution.getStatus())
            .build();
    }

    public Resolution getResolutionForAlert(Long id) {
        // For now, just return the latest resolution (customize as needed)
        return resolutionRepository.findFirstByOrderByCreatedAtDesc().orElse(null);
    }

    public com.alertsystem.dto.response.ActionPayloadResponse updateActionSteps(Long id, List<String> steps) {
        Resolution resolution = resolutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resolution", "id", id));

        if (resolution.getActionSteps() == null) {
            // Create new ActionPayload with only steps, no parameters or requiresApproval
            com.alertsystem.entity.ActionPayload newPayload = new com.alertsystem.entity.ActionPayload();
            newPayload.setSteps(steps);
            resolution.setActionSteps(newPayload);
        } else {
            // Only update the steps field, preserve everything else as-is
            resolution.getActionSteps().setSteps(steps);
        }

        resolution.setUpdatedAt(java.time.LocalDateTime.now());
        Resolution savedResolution = resolutionRepository.save(resolution);

        // Return the updated ActionPayload
        return com.alertsystem.dto.response.ActionPayloadResponse.builder()
                .parameters(savedResolution.getActionSteps().getParameters())
                .requiresApproval(savedResolution.getActionSteps().getRequiresApproval())
                .steps(savedResolution.getActionSteps().getSteps())
                .build();
    }
}
