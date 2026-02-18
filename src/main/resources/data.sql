-- Sample data for Alerts
-- Note: This file will be executed only in dev profile

-- Insert sample Alerts
INSERT INTO alerts (id, alert_name, status, agent, current_step, execution_time, progress, created_by, severity, ticket_id, description, classification, start_time, classification_reasoning, tool_name, risk_level, tool_description, command, estimated_downtime) VALUES
('650e8400-e29b-41d4-a716-446655440001', 'High CPU Usage Alert', 'IN_PROGRESS', 'monitoring-agent-01', 'Analyzing metrics', '00:05:30', 45, 'system', 'HIGH', 'TICK-1001', 'CPU usage exceeded 85% threshold on production server', 'INFRASTRUCTURE', '2024-01-15 10:30:00', 'Infrastructure issue detected based on CPU metrics', 'kubectl', 'MEDIUM', 'Scale deployment replicas', 'kubectl scale deployment api-service --replicas=5', '2 minutes'),
('650e8400-e29b-41d4-a716-446655440002', 'Database Connection Pool Exhausted', 'PENDING_APPROVAL', 'db-monitor-02', 'Waiting for approval', '00:02:15', 20, 'admin', 'CRITICAL', 'TICK-1002', 'Database connection pool reached maximum capacity', 'DATABASE', '2024-01-15 11:00:00', 'Database performance degradation detected', 'mysql', 'HIGH', 'Increase connection pool size', 'ALTER SYSTEM SET max_connections = 200', '5 minutes'),
('650e8400-e29b-41d4-a716-446655440003', 'Application Memory Leak', 'RESOLVED', 'app-monitor-03', 'Completed', '00:15:45', 100, 'devops', 'MEDIUM', 'TICK-1003', 'Memory usage continuously increasing in application pods', 'APPLICATION', '2024-01-15 09:00:00', 'Application-level memory management issue', 'kubectl', 'LOW', 'Restart affected pods', 'kubectl rollout restart deployment/app-service', '1 minute');

-- Insert alert tools used
INSERT INTO alert_tools_used (alert_id, tool) VALUES
('650e8400-e29b-41d4-a716-446655440001', 'Prometheus'),
('650e8400-e29b-41d4-a716-446655440001', 'Grafana'),
('650e8400-e29b-41d4-a716-446655440001', 'kubectl'),
('650e8400-e29b-41d4-a716-446655440002', 'MySQL Monitor'),
('650e8400-e29b-41d4-a716-446655440002', 'Database Profiler'),
('650e8400-e29b-41d4-a716-446655440003', 'Heap Analyzer'),
('650e8400-e29b-41d4-a716-446655440003', 'kubectl');

-- Insert supervisor progress
INSERT INTO supervisor_progress (id, alert_id, step, status, timestamp) VALUES
('750e8400-e29b-41d4-a716-446655440001', '650e8400-e29b-41d4-a716-446655440001', 'Alert received', 'COMPLETED', '2024-01-15 10:30:00'),
('750e8400-e29b-41d4-a716-446655440002', '650e8400-e29b-41d4-a716-446655440001', 'Initial analysis', 'COMPLETED', '2024-01-15 10:31:00'),
('750e8400-e29b-41d4-a716-446655440003', '650e8400-e29b-41d4-a716-446655440001', 'Solution proposed', 'IN_PROGRESS', '2024-01-15 10:33:00'),
('750e8400-e29b-41d4-a716-446655440004', '650e8400-e29b-41d4-a716-446655440002', 'Alert received', 'COMPLETED', '2024-01-15 11:00:00'),
('750e8400-e29b-41d4-a716-446655440005', '650e8400-e29b-41d4-a716-446655440002', 'Awaiting approval', 'PENDING', '2024-01-15 11:02:00');

-- Insert application progress
INSERT INTO application_progress (id, alert_id, step, status, timestamp, details) VALUES
('850e8400-e29b-41d4-a716-446655440001', '650e8400-e29b-41d4-a716-446655440001', 'Metrics collection', 'COMPLETED', '2024-01-15 10:30:30', 'Collected CPU metrics from all nodes'),
('850e8400-e29b-41d4-a716-446655440002', '650e8400-e29b-41d4-a716-446655440001', 'Root cause analysis', 'COMPLETED', '2024-01-15 10:32:00', 'Identified high traffic causing CPU spike'),
('850e8400-e29b-41d4-a716-446655440003', '650e8400-e29b-41d4-a716-446655440001', 'Scaling deployment', 'IN_PROGRESS', '2024-01-15 10:35:00', 'Scaling from 3 to 5 replicas'),
('850e8400-e29b-41d4-a716-446655440004', '650e8400-e29b-41d4-a716-446655440003', 'Pod restart initiated', 'COMPLETED', '2024-01-15 09:10:00', 'Rolling restart of 3 pods completed'),
('850e8400-e29b-41d4-a716-446655440005', '650e8400-e29b-41d4-a716-446655440003', 'Verification', 'COMPLETED', '2024-01-15 09:15:00', 'Memory usage stabilized after restart');

-- Insert sample Resolutions
INSERT INTO resolutions (id, issue_type, description, action_type, created_at, updated_at, created_by, status, command, risk_level, requires_approval) VALUES
('950e8400-e29b-41d4-a716-446655440001', 'High CPU Usage', 'Scale deployment to handle increased load', 'SCALE_DEPLOYMENT', '2024-01-15 10:00:00', '2024-01-15 10:00:00', 'system', 'ACTIVE', 'kubectl scale deployment {deployment_name} --replicas={count}', 'MEDIUM', true),
('950e8400-e29b-41d4-a716-446655440002', 'Database Connection Pool', 'Increase maximum database connections', 'UPDATE_CONFIG', '2024-01-15 10:30:00', '2024-01-15 10:30:00', 'admin', 'ACTIVE', 'ALTER SYSTEM SET max_connections = {value}', 'HIGH', true),
('950e8400-e29b-41d4-a716-446655440003', 'Memory Leak', 'Restart affected application pods', 'RESTART_PODS', '2024-01-15 09:00:00', '2024-01-15 09:00:00', 'devops', 'ACTIVE', 'kubectl rollout restart deployment/{deployment_name}', 'LOW', false);

-- Insert action parameters
INSERT INTO action_parameters (resolution_id, parameter) VALUES
('950e8400-e29b-41d4-a716-446655440001', 'deployment_name'),
('950e8400-e29b-41d4-a716-446655440001', 'replica_count'),
('950e8400-e29b-41d4-a716-446655440002', 'max_connections'),
('950e8400-e29b-41d4-a716-446655440003', 'deployment_name');
