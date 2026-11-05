# MyApp: E-commerce App with Product Recommendations

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-database-336791)
![Status](https://img.shields.io/badge/status-work%20in%20progress-yellow)

A REST API for an online shop: product catalog, shopping cart and order management, built with **Spring Boot** and **PostgreSQL**. The long-term goal is to add an **intelligent product recommendation engine** on top of the purchase data.

> **Status:** work in progress. The domain model, services, and the catalog and user APIs are done. Cart and order endpoints, authentication and the recommendation engine are next (see [Roadmap](#roadmap)).

## Features

- **Catalog**: categories and products with full CRUD
- **Product search**: combinable filters (category, keyword, price range, in-stock only) with pagination and sorting, built on JPA Specifications
- **Users**: registration data and user management
- **Shopping cart**: add, update, remove products, clear the cart; quantities are checked against stock
- **Orders**: checkout from the cart, order history, cancellation, status management
- **Consistent error handling**: a global exception handler turns domain errors (not found, duplicate, ...) into clean HTTP responses

## Tech stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.1 (Web MVC, Data JPA, Validation) |
| Database | PostgreSQL |
| Build | Maven (wrapper included) |
| Other | Lombok |

Planned: Spring Security + JWT, React frontend, Docker, GitHub Actions CI.

## Architecture

The application follows a classic layered architecture:

```
controller  →  service  →  repository  →  PostgreSQL
   (HTTP)     (business     (Spring Data
    DTOs       logic,         JPA)
              transactions)
```

- **`controller`**: REST endpoints, request validation (`@Valid`), HTTP status codes
- **`service`**: business rules and transactions
- **`repository`**: Spring Data JPA repositories
- **`model`**: JPA entities (`Produit`, `Categorie`, `User`, `Bucket`, `BucketLine`, `Order`, `OrderLine`)
- **`dto`**: request and response records, so entities are never exposed directly
- **`exception`**: custom exceptions and the global handler

## Design decisions

- **DTOs everywhere.** Entities stay internal. Clients send `...Request` objects and receive `...Response` records.
- **Price snapshot on orders.** Each order line stores the unit price at the time of purchase, so later price changes don't rewrite order history.
- **Transactional checkout.** Placing an order (read the cart, decrease stock, create the order with its lines, empty the cart) runs in a single transaction: if one step fails, nothing is saved.
- **Server-side authority.** Totals, prices and order status are computed by the server, never trusted from the client.
- **Flexible search.** Optional filters are combined dynamically with JPA Specifications instead of one repository method per combination.

## Getting started

### Prerequisites

- Java 17+
- Docker (or a local PostgreSQL instance)

### 1. Start the database

The default configuration expects PostgreSQL on port `5433`:

```bash
docker run --name myapp-db \
  -e POSTGRES_DB=myapp_db \
  -e POSTGRES_USER=myapp_user \
  -e POSTGRES_PASSWORD=secret \
  -p 5433:5432 -d postgres:16
```

### 2. Run the application

```bash
git clone https://github.com/Famous2705/myapp.git
cd myapp
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080`. Tables are created automatically on first start (`ddl-auto=update`).

### 3. Try it

```bash
# create a category
curl -X POST http://localhost:8080/api/v1/categories \
  -H "Content-Type: application/json" \
  -d '{"nom": "Shoes"}'

# list categories
curl http://localhost:8080/api/v1/categories

# search products
curl "http://localhost:8080/api/v1/products?keyword=run&minPrice=20&maxPrice=80&availableOnly=true&page=0&size=10&sort=prix,asc"
```

## API overview

Base path: `/api/v1`

| Resource | Endpoints |
|---|---|
| **Categories** | `POST /categories` · `GET /categories` · `GET /categories/{id}` · `PUT /categories/{id}` · `DELETE /categories/{id}` |
| **Products** | `POST /products` · `GET /products` (search) · `GET /products/{id}` · `PUT /products/{id}` · `DELETE /products/{id}` |
| **Users** | `POST /users` · `GET /users` · `GET /users/{id}` · `DELETE /users/{id}` |
| **Cart** | in progress |
| **Orders** | in progress |

**Search parameters** for `GET /products` (all optional): `categoryId`, `keyword`, `minPrice`, `maxPrice`, `availableOnly`, plus `page`, `size`, `sort`.

## Roadmap

- [x] Domain model and repositories
- [x] Services (catalog, users, cart, orders)
- [x] Catalog and user REST endpoints
- [ ] Cart and order REST endpoints
- [ ] Complete exception handling (insufficient stock, forbidden operations)
- [ ] Unit and integration tests
- [ ] Authentication and authorization (Spring Security, JWT, user/admin roles)
- [ ] Password hashing at registration
- [ ] Recommendation engine (purchase history, "frequently bought together", similar products)
- [ ] React frontend
- [ ] Docker Compose and CI with GitHub Actions
- [ ] API documentation (OpenAPI / Swagger)

## Author

**Famous**: Computer Science student at Saarland University.
GitHub: [@Famous2705](https://github.com/Famous2705)
