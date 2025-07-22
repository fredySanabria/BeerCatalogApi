# BeerCatalog API

This project is a Java 21 RESTful API for managing beers and manufacturers, built using Spring Boot 3.5.4 and designed with a clean, maintainable Hexagonal Architecture (Ports and Adapters).

## 💾 Main Technologies

- Java 21
- Spring Boot 3.5.4
- Spring MVC
- Spring Security
- Criteria API
- JPA & Hibernate
- H2 / PostgreSQL (configurable)
- Docker & Kubernetes (Minikube compatible)
- OpenAPI (Swagger UI)

---

## 💻 Architecture Overview

The project follows Hexagonal Architecture:

- **Domain Layer**: Contains core business logic and immutable domain models using Java `records`.
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

- RESTful CRUD for Beers and Manufacturers
- Domain-driven validation (e.g. manufacturer name must not contain numbers)
- DTOs and mappers for transport and transformation
- Global exception handler with structured JSON error responses
- Swagger UI available for easy testing also Postman Collections added
- Kubernetes-ready (with Secrets, Service, Deployment, Ingress, etc.)

---

## ⚙️ Running Locally

> ℹ️ **Note:** This application uses Spring profiles. When running locally, you should activate a profile using JVM options.

```bash
git clone https://github.com/fredySanabria/BeerCatalogApi.git
cd BeerCatalogApi
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=test"
```

Then go to: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

OpenAPI docs: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🧪 Testing

- Unit tests for domain, use cases
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

![Jacoco execution](/images/jacoco.png)

---

## 📬 Postman Collection

A ready-to-use Postman collection is included in the repository:

- `assets/BeerCatalogAPI-Admin.json`
- `assets/BeerCatalogAPI-Manufacturer.json`

You can import this into Postman and test all endpoints easily.

---

## ☁️ Docker & Kubernetes

Please follow the deployment instructions in 
[deployment](deployment.md)

### Minikube Setup
For security reasons I decide don't upload secret YAMl file creation 

## ☁️ AWS

To create the postgres instance was necessary to perform the following tasks:

✅ Create VPC

✅ Create 2 public subnets

✅ Create  subnet group

✅ Create security group

✅ Create internet gateway

✅ Create RD5 postgres image

# 🙋 Considerations from the Author 

Thanks in advance to review my code. I really appreciate it.

* Security: I'd used 3 users in memory each with different roles ADMIN, MANUFACTURER, ANONYMOUS. it's important to mention that just created to test proposes. The correct approach should use a strong user repository, authentication and authorization (OAuth2 implementation for example).

* Databases: I create an embed database H2 to run integration test it's necessary to run VM options with this option  
```bash
-Dspring.profiles.active=test 
```
  for dev purposes I create other profile that run with AWS RD5 postgres database:

```bash
-Dspring.profiles.active=aws
```
* Advance search: you can use different parameters for your search, also sorting, pagination and in the ABV case a range:

```bash
/api/manufacturers?/api/manufacturers?name=page=0&size=10&sort=name,desc
/api/beers?ABVMin=4.5&ABVMax=6.0&page=0&size=10&sort=ABV,asc
```



