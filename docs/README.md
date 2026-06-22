# TaskTracker

## Overview

TaskTracker is a Spring Boot REST API for managing users and tasks.

Features:

* Create users
* View users
* Create tasks
* View tasks
* Update tasks
* Delete tasks
* Filter tasks by status
* Filter tasks by owner
* Health and readiness endpoints

---

## Prerequisites

* Java 17+
* Maven
* PostgreSQL

---

## Setup

Clone repository:

git clone <repository-url>

cd tasktracker

Create PostgreSQL database.

Set environment variables:

DB_URL=jdbc:postgresql://localhost:5432/tasktracker

DB_USER=postgres

DB_PASSWORD=password

---

## Running the Application

mvn spring-boot:run

Swagger UI:

http://localhost:8080/swagger-ui.html

---

## Running Tests

mvn clean test

---

## Environment Variables

| Variable    | Description         |
| ----------- | ------------------- |
| DB_URL      | PostgreSQL JDBC URL |
| DB_USER     | Database username   |
| DB_PASSWORD | Database password   |

---

## API Endpoints

### Users

POST /users

GET /users/{id}

### Tasks

POST /tasks

GET /tasks

GET /tasks/{id}

PUT /tasks/{id}

DELETE /tasks/{id}

### Health

GET /healthz

GET /readyz

---

## Troubleshooting

### Application fails to start

Verify:

* PostgreSQL is running
* DB_URL is correct
* DB_USER is correct
* DB_PASSWORD is correct

### Swagger not loading

Verify application is running on port 8080.

### Database connection error

Check PostgreSQL service and credentials.
