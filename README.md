# Alert System - Spring Boot RESTful API

A comprehensive RESTful API service for managing alerts, resolutions, and Kubernetes pod monitoring built with Spring Boot 3.x, Spring Data JPA, and MySQL.

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Future Enhancements](#future-enhancements)

## ✨ Features

- **Alert Management**: Create and retrieve alerts with detailed tracking
- **Resolution Management**: Manage issue resolutions with action payloads
- **Kubernetes Pod Monitoring**: Track and manage Kubernetes pod status
- **Clean Architecture**: Separation of concerns with Controller, Service, Repository, and Entity layers
- **DTO Pattern**: Request/Response mapping for clean API contracts
- **Global Exception Handling**: Structured error responses
- **API Documentation**: Interactive Swagger/OpenAPI documentation
- **Validation**: Jakarta Bean Validation for request validation
- **Sample Data**: Pre-loaded seed data for development

## 🛠 Tech Stack

- **Java**: 17+
- **Spring Boot**: 3.2.0
- **Spring Web**: RESTful API development
- **Spring Data JPA**: Database operations
- **MySQL**: Relational database
- **Lombok**: Reduce boilerplate code
- **SpringDoc OpenAPI**: API documentation (Swagger UI)
- **Maven**: Build and dependency management

## 📦 Prerequisites

Before running this application, ensure you have:

- **Java 17** or higher installed
- **Maven 3.6+** installed
- **MySQL 8.0+** installed and running
- **Git** (optional, for cloning)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd alert-system
```

### 2. Configure MySQL Database

Create a MySQL database:

```sql
CREATE DATABASE alert_system;
```

Update database credentials in `src/main/resources/application-dev.yml` if needed:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/alert_system
    username: root
    password: root
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api/v1`

### 5. Access Swagger UI

Open your browser and navigate to:

```
http://localhost:8080/api/v1/swagger-ui.html
```

## 📚 API Documentation

### Swagger/OpenAPI

Interactive API documentation is available at:
- **Swagger UI**: `http://localhost:8080/api/v1/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api/v1/api-docs`

## 📁 Project Structure

```
alert-system/
├── src/
│   ├── main/
│   │   ├── java/com/alertsystem/
│   │   │   ├── AlertSystemApplication.java
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AlertController.java
│   │   │   │   ├── ResolutionController.java
│   │   │   │   └── KubernetesPodController.java
│   │   │   ├── service/
│   │   │   │   ├── AlertService.java
│   │   │   │   ├── ResolutionService.java
│   │   │   │   └── KubernetesPodService.java
│   │   │   ├── repository/
│   │   │   │   ├── AlertRepository.java
│   │   │   │   ├── ResolutionRepository.java
│   │   │   │   └── KubernetesPodRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── Alert.java
│   │   │   │   ├── SupervisorProgress.java
│   │   │   │   ├── ApplicationProgress.java
│   │   │   │   ├── ToolExecution.java
│   │   │   │   ├── Resolution.java
│   │   │   │   ├── ActionPayload.java
│   │   │   │   └── KubernetesPod.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── enums/
│   │   │   │   ├── AlertStatus.java
│   │   │   │   ├── Severity.java
│   │   │   │   ├── Classification.java
│   │   │   │   ├── ResolutionStatus.java
│   │   │   │   └── PodStatus.java
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       ├── ResourceNotFoundException.java
│   │   │       └── ErrorResponse.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       └── data.sql
│   └── test/
├── pom.xml
└── README.md
```

## 🗄 Database Schema

### Main Entities

1. **alerts**: Core alert information
2. **supervisor_progress**: Alert supervisor tracking
3. **application_progress**: Application-level progress tracking
4. **resolutions**: Issue resolution definitions
5. **kubernetes_pods**: Kubernetes pod monitoring

### Relationships

- Alert → SupervisorProgress (One-to-Many)
- Alert → ApplicationProgress (One-to-Many)
- Alert → ToolExecution (Embedded)
- Resolution → ActionPayload (Embedded)

## 🔌 API Endpoints

### Alerts Module

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/alerts` | Get all alerts |
| GET | `/api/v1/alerts/{id}` | Get alert by ID |
| POST | `/api/v1/alerts` | Create new alert |

### Resolutions Module

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/resolutions` | Get all resolutions |
| GET | `/api/v1/resolutions/{id}` | Get resolution by ID |
| POST | `/api/v1/resolutions` | Create new resolution |

### Kubernetes Pods Module

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/kubernetes/pods` | Get all pods |
| GET | `/api/v1/kubernetes/pods/{id}` | Get pod by ID |
| POST | `/api/v1/kubernetes/pods` | Create new pod |

## 📝 Sample Request Examples

### Create Alert

```bash
curl -X POST http://localhost:8080/api/v1/alerts \
  -H "Content-Type: application/json" \
  -d '{
    "alertName": "High Memory Usage",
    "status": "PENDING_APPROVAL",
    "severity": "HIGH",
    "createdBy": "system",
    "description": "Memory usage exceeded 90%",
    "classification": "INFRASTRUCTURE",
    "progress": 25
  }'
```

### Create Resolution

```bash
curl -X POST http://localhost:8080/api/v1/resolutions \
  -H "Content-Type: application/json" \
  -d '{
    "issueType": "Memory Leak",
    "actionType": "RESTART_SERVICE",
    "createdBy": "admin",
    "status": "ACTIVE",
    "description": "Restart service to clear memory",
    "actionPayload": {
      "command": "systemctl restart app-service",
      "riskLevel": "MEDIUM",
      "requiresApproval": true
    }
  }'
```

### Create Kubernetes Pod

```bash
curl -X POST http://localhost:8080/api/v1/kubernetes/pods \
  -H "Content-Type: application/json" \
  -d '{
    "name": "nginx-pod-123",
    "namespace": "default",
    "status": "RUNNING",
    "age": "2d",
    "restarts": 0,
    "cpu": "100m",
    "memory": "256Mi",
    "node": "node-1",
    "ip": "10.244.0.10",
    "labels": {
      "app": "nginx",
      "version": "1.0"
    }
  }'
```

## ⚙️ Configuration

### Application Profiles

- **Default Profile**: `application.yml`
- **Development Profile**: `application-dev.yml` (active by default)

### Key Configuration Properties

```yaml
server:
  port: 8080
  servlet:
    context-path: /api/v1

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/alert_system
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

## 🧪 Running Tests

```bash
mvn test
```

## 🔒 Error Handling

The API returns structured error responses:

```json
{
  "status": 400,
  "message": "Invalid request parameters",
  "data": {
    "errors": ["Alert name is required"]
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

### HTTP Status Codes

- `200 OK`: Successful GET request
- `201 Created`: Successful POST request
- `400 Bad Request`: Validation error
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

## 🚀 Future Enhancements

- [ ] JWT-based authentication and authorization
- [ ] Update and Delete operations for all entities
- [ ] Pagination and sorting for list endpoints
- [ ] Advanced filtering and search capabilities
- [ ] Real-time notifications using WebSocket
- [ ] Metrics and monitoring integration
- [ ] Docker containerization
- [ ] CI/CD pipeline setup
- [ ] Integration tests
- [ ] Rate limiting

## 📄 License

This project is licensed under the MIT License.

## 👥 Contributors

- Alert System Team

## 📧 Support

For support, email support@alertsystem.com or open an issue in the repository.

---

**Built with ❤️ using Spring Boot**
