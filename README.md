# Medical Diagnosis Backend

A Spring Boot backend API for managing patients and performing medical symptom-based diagnosis using the **ApiMedic Symptom Checker API** (ApiMedic).  
The system allows searching/creating patients, requesting possible diagnoses from symptoms, and validating/storing diagnosis results in PostgreSQL.


## Features

- Patient management
- Integration with **ApiMedic Symptom Checker API** for diagnosis suggestions
- Diagnosis result validation & persistence
- PostgreSQL database for storing patients and diagnosis history
- Fully Dockerized (app + database)
- CORS configured for frontend integration

## Tech Stack

- **Backend**: Spring Boot 3.x (Java 17+)
- **Database**: PostgreSQL 15
- **Containerization**: Docker + Docker Compose

## Prerequisites

1. **Docker** & **Docker Compose** installed

## API Endpoints

All endpoints are prefixed with `/api`

| Method | Endpoint              | Description                                          | Auth? | Request Example                                                       | Response Example (200 OK)                          | Status Codes          |
|--------|-----------------------|------------------------------------------------------|-------|-----------------------------------------------------------------------|----------------------------------------------------|-----------------------|
| GET    | `/patient/search`     | Search patients by various criteria                  | No    | `GET /api/patient/search?name=John`                                   | `[{ "id": 1, "name": "John Doe", ... }]`           | 200, 400, 500         |
| POST   | `/patient`            | Create a new patient                                 | No    | `{ "name": "Jane Smith", "age": 28, "gender": "FEMALE" }`             | `{ "id": 2, "name": "Jane Smith", ... }`           | 201, 400, 409 (duplicate) |
| POST   | `/diagnosis`          | Retrieve possible diagnoses from symptoms            | No    | `{ "symptoms": stomach pain, "gender": "MALE", "dateOfBirth": 1992 }` | `{ "diagnoses": [{ "id": 45, "name": "Common Cold", "probability": 0.82 }, ...] }` | 200, 400, 503 (API down) |
| POST   | `/diagnosis/validate` | Validate & persist selected diagnosis                | No    | `{ "diagnosisId": 45, "patientId": 1, "status": Valid }`              | `{ "status": "validated", "diagnosisId": 45 }`     | 200, 400, 404         |


## Quick Start (Recommended - Docker)

1. Clone the repository
   ```bash
   git clone <your-repo-url>
   cd medical-diagnosis-backend80:8080 medical-diagnosis
   docker compose up -d --build