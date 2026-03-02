package com.alertsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_id", unique = true)
    private String ticketId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "severity")
    private String severity;

    @Column(name = "issue_type")
    private String issueType;

    @Column(name = "ticket", columnDefinition = "TEXT")
    private String ticket;

    @CreationTimestamp
    @Column(name = "inserted_at", updatable = false)
    private LocalDateTime insertedAt;

    @Column(name = "classification")
    private String classification;

    @Column(name = "confidence")
    private Float confidence;

    @Column(name = "reasoning", columnDefinition = "TEXT")
    private String reasoning;

    @Column(name = "agent_name")
    private String agentName;

    @Column(name = "status")
    private String status;

    @Column(name = "source")
    private String source;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;
}
