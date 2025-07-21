# BeerCatalog API

This project is a Java 21 RESTful API for managing products and manufacturers, built using Spring Boot 3.5.4 and designed with a clean, maintainable Hexagonal Architecture (Ports and Adapters).

## 🚀 Main Technologies

- Java 21
- Spring Boot 3.5.4
- Spring MVC
- JPA & Hibernate
- H2 / PostgreSQL (configurable)
- Docker & Kubernetes (Minikube compatible)
- OpenAPI (Swagger UI)

---

## 🧱 Architecture Overview

The project follows Hexagonal Architecture:

- **Domain Layer**: Contains core business logic and immutable domain models using Java `record`s.
- **Application Layer**: Use cases (services) that coordinate business rules.
- **Ports**: Interfaces for persistence or external systems.
- **Adapters**:
    - **Inbound**: REST controllers
    - **Outbound**: JPA repositories

This design separates business logic from infrastructure for better testability and flexibility.

---

## 🧩 Project Structure

```plaintext
src/main/java
├── domain             # Core domain logic (Product, Manufacturer)
├── application        # Use cases and services
├── infrastructure     # Persistence adapters (JPA)
├── web                # REST controllers and DTOs
├── config             # Spring config & Bean setup
```

---

## ✅ Features

- RESTful CRUD for Products and Manufacturers
- Domain-driven validation (e.g. manufacturer name must not contain numbers)
- DTOs and mappers for transport and transformation
- Optional usage for safe repository queries
- Global exception handler with structured JSON error responses
- Swagger UI available for easy testing
- Kubernetes-ready (with Secrets, ConfigMaps, Ingress, etc.)

---

## ⚙️ Running Locally

> ℹ️ **Note:** This application uses Spring profiles. When running locally, you should activate a profile using JVM options.

```bash
git clone https://github.com/your-user/beercatalog-api.git
cd beercatalog-api
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=local"
```

Then go to: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧪 Testing

- Unit tests for domain and use cases
- Integration tests for controllers using MockMvc
- Validation and error path tests
- Code coverage via Jacoco

Run tests:

```bash
./mvnw test
```

To generate the Jacoco coverage report:

```bash
./mvnw test jacoco:report
```

The HTML report will be available at:

```
target/site/jacoco/index.html
```

---

## 📬 Postman Collection

A ready-to-use Postman collection is included in the repository:

- `postman/BeerCatalog-API.postman_collection.json`

You can import this into Postman and test all endpoints easily.

---

## ☁️ Docker & Kubernetes

### Docker Build

```bash
docker build -t beercatalog-api:0.0.1-SNAPSHOT .
```

### Minikube Setup

```bash
minikube start
kubectl apply -f k8s/
minikube service api-service
```

### Example Configurations:

- Deployment YAML with profile: `-Dspring.profiles.active=aws`
- Secrets YAML to inject DB credentials
- Ingress with host: `beercatalog.local`

Update your `hosts` file:

```bash
127.0.0.1 beercatalog.local
```

---


