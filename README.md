# Finance Tracker — Expense Service

Spring Boot service that owns **expense records**: create, update, and list spends per user. Expenses can arrive from the **HTTP API** (UI “add spend”) or from **Kafka** when the DS service extracts a structured expense from natural language.

Part of the Finance Tracker microservices app (Kong + Kafka + MySQL).

**System map:** see [ARCHITECTURE.md in Auth](https://github.com/sukeerthreddy12/FinanceTrackerApplication/blob/main/ARCHITECTURE.md)

## What this service does

- Persist expenses in MySQL
- Expose REST under `/expense/v1` for the frontend (via Kong)
- Consume Kafka topic `expense_service` (messages produced by DS after LLM extraction)
- Idempotent-style create/update from consumed `ExpenseDto` events

It does **not** call the LLM — DS does that. This service is the source of truth for stored spends.

## Main HTTP API

| Method | Path | Purpose |
|--------|------|---------|
| POST | `/expense/v1/addExpense` | Add an expense |
| GET | `/expense/v1/` (and related get paths) | List / fetch expenses |

Frontend example: `GET /expense/v1/getExpense` through Kong.

## Kafka

| Direction | Topic | Meaning |
|-----------|-------|---------|
| Consume | `expense_service` | Structured expense from DS/LLM pipeline |

## Stack

- Java / Spring Boot
- MySQL
- Kafka consumer

## Setup

```bash
cp .env.example .env
# edit .env with your MySQL and Kafka hosts
./gradlew bootRun
```

Default port: **9820**.

## Env

Copy `.env.example` → `.env`. Never commit `.env`.

## Related repos

- [DS Service](https://github.com/sukeerthreddy12/FinanceTrackerDsService) (produces expense events)
- [Auth](https://github.com/sukeerthreddy12/FinanceTrackerApplication) · [User](https://github.com/sukeerthreddy12/FinanceTrackerUserService) · [Frontend](https://github.com/sukeerthreddy12/FinanceTrackerFrontend)
