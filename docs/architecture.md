# Architecture

## High Level Diagram

Client
|
v
Spring Boot REST API
|
v
PostgreSQL Database

## Request Flow

1. Client sends HTTP request.
2. Controller receives request.
3. Controller calls Service.
4. Service performs business logic.
5. Service calls Repository.
6. Repository communicates with PostgreSQL.
7. Response is returned to the client.

## Components

### Controllers

* UserController
* TaskController
* HealthController

### Services

* UserService
* TaskService

### Repositories

* UserRepository
* TaskRepository

### Database

PostgreSQL stores users and tasks.

## Logging

Every request receives a request_id.

Logs are emitted in JSON format containing:

* timestamp
* level
* message
* request_id
