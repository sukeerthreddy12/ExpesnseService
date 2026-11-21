# Finance Tracker — Expense Service

Spring Boot service for expenses. Part of the Finance Tracker application (Kafka + MySQL + Kong).

## Stack

- Java / Spring Boot
- MySQL
- Kafka (consumes expense events)

## Setup

```bash
cp .env.example .env
# edit .env with your MySQL and Kafka hosts
./gradlew bootRun
```

Default port: `9820`.

## Env

Copy `.env.example` → `.env`. Never commit `.env`.
