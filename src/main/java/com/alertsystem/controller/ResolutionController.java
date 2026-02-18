package com.alertsystem.controller;

import com.alertsystem.dto.request.ResolutionRequest;
import com.alertsystem.dto.response.ResolutionResponse;
import com.alertsystem.service.ResolutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resolutions")
@RequiredArgsConstructor
@Tag(name = "Resolutions", description = "Resolution management APIs")
public class ResolutionController {
    
    private final ResolutionService resolutionService;
    
    @GetMapping
    @Operation(summary = "Get all resolutions", description = "Retrieve a list of all resolutions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ResolutionResponse>> getAllResolutions() {
        List<ResolutionResponse> resolutions = resolutionService.getAllResolutions();
        return ResponseEntity.ok(resolutions);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get resolution by ID", description = "Retrieve a specific resolution by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resolution"),
            @ApiResponse(responseCode = "404", description = "Resolution not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResolutionResponse> getResolutionById(@PathVariable Long id) {
        ResolutionResponse resolution = resolutionService.getResolutionById(id);
        return ResponseEntity.ok(resolution);
    }
    
    @PostMapping
    @Operation(summary = "Create a new resolution", description = "Create a new resolution with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resolution created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResolutionResponse> createResolution(@Valid @RequestBody ResolutionRequest request) {
        ResolutionResponse createdResolution = resolutionService.createResolution(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResolution);
    }

    @PatchMapping("/{id}/action-steps")
    @Operation(summary = "Update action steps for a resolution", description = "Update the action steps of a specific resolution by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Action steps updated successfully"),
            @ApiResponse(responseCode = "404", description = "Resolution not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    public ResponseEntity<com.alertsystem.dto.response.ActionPayloadResponse> updateActionSteps(
            @PathVariable Long id,
            @Valid @RequestBody com.alertsystem.dto.request.UpdateActionStepsRequest request) {
        com.alertsystem.dto.response.ActionPayloadResponse response = resolutionService.updateActionSteps(id, request.getSteps());
        return ResponseEntity.ok(response);
    }
}
