package com.alertsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_agent_execution_summary")
public class TaskAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_agent_alert_id")
    private Long taskAgentAlertId;

    @Column(name = "task_agent_execution_nodes", columnDefinition = "JSON")
    @Convert(converter = ExecutionNodesConverter.class)
    private List<ExecutionNode> taskAgentExecutionNodes;

    @Column(name = "task_agent_full_result", columnDefinition = "JSON")
    @Convert(converter = FullResultConverter.class)
    private FullResult taskAgentFullResult;

    @Column(name = "task_agent_start_time")
    private LocalDateTime taskAgentStartTime;

    @Column(name = "task_agent_end_time")
    private LocalDateTime taskAgentEndTime;

    @Column(name = "task_agent_status")
    private String taskAgentStatus;

    @Column(name = "confidence_score")
    private Double confidenceScore;

    @Column(name = "task_agent_total_nodes")
    private Integer taskAgentTotalNodes;

    @Column(name = "task_agent_successful_nodes")
    private Integer taskAgentSuccessfulNodes;

    @Column(name = "task_agent_failed_nodes")
    private Integer taskAgentFailedNodes;
}
