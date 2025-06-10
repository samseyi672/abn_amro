# Recipe Management System Documentation

## Overview

The Recipe Management System is designed using a robust microservices architecture to ensure scalability, maintainability, and flexibility. The system comprises four core microservices, each responsible for a specific domain within the application ecosystem. This architecture allows each service to evolve independently while maintaining seamless integration.

## Microservices Architecture

The system consists of the following microservices:

1. **API Gateway**
    - Handles request routing from clients to the appropriate microservices.
    - Provides client-side load balancing for optimal performance and reliability.

2. **Service Discovery**
    - Monitors the health and availability of each microservice instance.
    - Facilitates dynamic registration and deregistration of services, enabling robust failover and scaling.

3. **User Management**
    - Manages user registration, authentication, and user profile operations.
    - Ensures secure onboarding and management of users.

4. **Recipe Management**
    - Responsible for all operations related to recipe creation, retrieval, updating, and deletion.
    - Acts as the core business service for managing recipes.

## System Artifacts

- **Architecture Diagram:** A visual representation of the system architecture is provided in the project artifacts, illustrating the interaction and flow between the microservices.
- **API Documentation:** 
    - Comprehensive Swagger/OpenAPI documentation is available for both the User Management and Recipe Management microservices.
    - These documents detail sample requests and responses, enabling easy integration and testing.

## Deployment and Containerization

The system leverages Docker Compose for container orchestration, providing two distinct deployment configurations:

1. **Full Observability and Monitoring Deployment**
    - Includes monitoring and observability tools.
    - This deployment consumes more system memory and is recommended for production environments or deep diagnostics.

2. **Core Microservices Deployment**
    - Spins up only the four essential microservices along with their corresponding databases.
    - Offers a lightweight setup suitable for development and testing environments.

### Running the Services

Ensure Docker and Docker Compose are installed on your machine.

1. **Start the Services:**
    ```bash
    docker compose up
    ```

2. **Stop the Services:**
    ```bash
    docker compose down
    ```

> **Note:** Use the second (internal) Docker Compose file for a lightweight deployment that excludes observability and monitoring components.

## Accessing API Documentation

- **User Management Microservice Swagger UI:**
    - [http://localhost:8090/swagger-ui.html](http://localhost:8090/swagger-ui.html)

- **Recipe Management Microservice Swagger UI:**
    - [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

## Support

If you require further clarification or have any questions regarding setup, deployment, or usage, please do not hesitate to reach out.

---
-----------------------------------------------------------------------------------------------------------------------------------------
Feedback and Bug Reporting
We are committed to continuously improving the Recipe Management System. If you encounter any bugs, issues, or unexpected behavior, or if you have suggestions for improvement, please let us know. Your feedback is highly valued and helps us enhance the quality and reliability of this system.

To report a bug or provide feedback, please reach out through the designated communication channels or open an issue in the project repository.

We appreciate your input and are always open to corrections and suggestions.
------------------------------------------------------------------------------------------------------------------------------------------

**Thank you for the opportunity.**







