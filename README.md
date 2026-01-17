# Medical Diagnosis Backend

## Features
- Patient search
- ApiMedic diagnosis integration
- Diagnosis validation
- PostgreSQL persistence
- Dockerized deployment

## Endpoints
GET  /api/patients/search
POST /api/diagnosis
POST  /api/diagnosis/validate

## Run
docker build -t medical-diagnosis .
docker run -p 8080:8080 medical-diagnosis