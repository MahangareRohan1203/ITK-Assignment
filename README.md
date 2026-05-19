# Payment Service

A Spring Boot-based RESTful service for managing digital wallets. It supports deposits, withdrawals, and balance inquiries with data persistence in PostgreSQL and versioned migrations via Liquibase.

## Core Features
- **Wallet Operations:** Deposit and withdraw funds with concurrency handling.
- **Balance Inquiries:** Real-time wallet balance checks.
- **Database Migrations:** Automated schema management using Liquibase.
- **Containerized Environment:** Full setup available via Docker Compose.
- **Testing:** Integration tests using Testcontainers for realistic environment simulation.

## Tech Stack
- **Language:** Java 17
- **Framework:** Spring Boot 3.5.15-SNAPSHOT
- **Persistence:** Spring Data JPA, Hibernate, PostgreSQL
- **Migration:** Liquibase
- **Build Tool:** Maven
- **Containerization:** Docker, Docker Compose
- **Utilities:** Lombok, Jakarta Validation

---

## Getting Started

### Prerequisites
- JDK 17+
- Maven 3.8+
- Docker & Docker Compose

### Option 1: Using Docker (Recommended)
The quickest way to get the service up and running is using Docker Compose. This will spin up both the PostgreSQL database and the Spring Boot application.

1. Clone the repository.
2. Ensure the `.env` file is present in the root directory (one is provided by default).
3. Run the following command:
   ```bash
   docker compose up --build
   ```
The service will be available at `http://localhost:8080`.

### Option 2: Local Development
If you prefer running the application locally:

1. Start a PostgreSQL instance (you can use the `postgres` service from `docker-compose.yml` by running `docker compose up postgres -d`).
2. Update the database credentials in `payment-service/src/main/resources/application.yaml` or set the corresponding environment variables.
3. Build and run the application:
   ```bash
   cd payment-service
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

### Sample Data
I have kept this wallet_id during container initialization: 
- `550e8400-e29b-41d4-a716-446655440000` (Balance: 1000.00)
- `550e8400-e29b-41d4-a716-446655440001` (Balance: 5000.00)
- `550e8400-e29b-41d4-a716-446655440002` (Balance: 150.50)

---

## API Documentation

### 1. Perform Wallet Operation
Executes a deposit or withdrawal.

- **URL:** `/api/v1/wallet`
- **Method:** `POST`
- **Body:**
  ```json
  {
      "walletId": "550e8400-e29b-41d4-a716-446655440000",
      "operationType": "DEPOSIT",
      "amount": 1000.00
  }
  ```
- **Validations:**
    - `operationType` must be `DEPOSIT` or `WITHDRAW`.
    - `amount` must be positive.
    - `walletId` must exist.
    - Withdrawals fail if funds are insufficient.

### 2. Get Wallet Balance
Retrieves the current balance for a specific wallet.

- **URL:** `/api/v1/wallets/{wallet_uuid}`
- **Method:** `GET`
- **Response:**
  ```json
  {
      "walletId": "550e8400-e29b-41d4-a716-446655440000",
      "balance": 5000.50
  }
  ```

### 3. Health Check
- **URL:** `/api/v1/hello`
- **Method:** `GET`

---

## Testing
The project includes integration tests that use **Testcontainers** to spin up a temporary PostgreSQL instance.

To run tests:
```bash
cd payment-service
./mvnw test
```

## Postman Collection
A pre-configured Postman collection is available at the root: `Itk-assignment.postman_collection.json`. Import this into Postman to quickly test all endpoints.

## Database Migrations
Migrations are handled by Liquibase. On startup, the application checks `db/changelog/db-changelog-master.xml` and applies any pending changes. Initial test data (if any) is managed via `db-changelog-seed.xml`.
