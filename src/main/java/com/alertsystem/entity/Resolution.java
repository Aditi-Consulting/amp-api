package com.alertsystem.entity;

import com.alertsystem.enums.ResolutionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Convert;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resolutions")
public class Resolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requires_approval")
    private Boolean requiresApproval;

    @Column(name = "issue_type")
    private String issueType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ResolutionStatus status;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "action_steps", columnDefinition = "TEXT")
    @Convert(converter = ActionPayloadConverter.class)
    private ActionPayload actionSteps;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;
}
