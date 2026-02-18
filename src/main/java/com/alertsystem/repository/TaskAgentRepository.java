package com.alertsystem.repository;

import com.alertsystem.entity.TaskAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAgentRepository extends JpaRepository<TaskAgent, Long> {
    Optional<TaskAgent> findTopByTaskAgentAlertIdOrderByTaskAgentStartTimeDesc(Long alertId);
    List<TaskAgent> findByTaskAgentAlertIdOrderByTaskAgentStartTimeDesc(Long alertId);
}
