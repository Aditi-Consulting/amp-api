package com.alertsystem.repository;

import com.alertsystem.entity.TaskAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAgentRepository extends JpaRepository<TaskAgent, Long> {
    // Get latest record by ID (most reliable - uses auto-increment)
    Optional<TaskAgent> findTopByTaskAgentAlertIdOrderByIdDesc(Long alertId);
    
    // Get all records sorted by ID descending (newest first)
    List<TaskAgent> findByTaskAgentAlertIdOrderByIdDesc(Long alertId);
}
