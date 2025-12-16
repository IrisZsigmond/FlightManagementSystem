# Flight Management System – Architectural Overview

## Purpose

This project implements a complete Flight Management System using Spring Boot, Spring MVC, Spring Data JPA, Hibernate ORM, Thymeleaf, and a modular layered architecture.

It follows industry standards for:

- separation of concerns  
- domain logic encapsulation  
- robust validation  

---

# Repository Layer (JpaRepository): Purpose & Behavior

## Why `JpaRepository<T, ID>` Is Used

`JpaRepository<T, ID>` is a Spring Data abstraction that provides:

- out-of-the-box CRUD operations  
- pagination and sorting  
- dynamic query derivation  
- automatic implementation via Hibernate  

Using `JpaRepository` ensures:

- less boilerplate code  
- clean data access  
- tight ORM integration  
- transaction-aware repository calls  

---

## Why ORM (Hibernate / JPA) Is Used

Hibernate is used as the JPA provider to map Java classes to relational database tables.

Benefits:

- object-relational mapping without SQL boilerplate  
- automatic schema generation  
- management of relationships (OneToMany, ManyToOne, inheritance)  
- caching and lazy loading  
- transaction management  

This allows the domain model to remain expressive and database-independent.

---

## Why Hibernate Inheritance Is Used for `Staff`

`Staff` is the parent entity for:

- AirlineEmployee  
- AirportEmployee  

Using:

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue


ensures:

- a single table for all staff  
- unified ID restrictions  
- clean polymorphism  
- efficient queries  

This enables cross-validation of ID uniqueness across all staff types.

---

## Why Real Database Integration Matters

A real relational database (MySQL or PostgreSQL) provides:

- data persistence  
- referential integrity  
- enforceable constraints  
- reliable transactional behavior  

Connection is configured through:

- `spring.datasource.url`  
- `spring.datasource.username`  
- `spring.datasource.password`  
- `spring.jpa.*`  

Hibernate manages:

- SQL generation  
- entity state transitions  
- dirty checking  
- transaction boundaries  

---

# Service Layer: Structure & Rationale

## Purpose of the Service Layer

The service layer acts between controllers and repositories and handles:

- business logic  
- transactional guarantees  
- validator invocation  
- multi-entity interactions  
- protection from invalid data  

Controllers **must not** contain business rules.

---

## Pattern Used
```
| Layer | Responsibility |
|-------|----------------|
| **EntityService Interface** | Declares CRUD + domain operations |
| **EntityServiceImpl** | Implements logic, calls validators, orchestrates repositories |
| **Validator** | Ensures domain correctness before persistence |
| **Repository** | Provides database access using JPA |
```
This ensures maintainable, testable, and clean architecture.

---

## Why Services Use `@Transactional`

`@Transactional` guarantees:

- atomic operations  
- rollback on failure  
- consistent database state  
- correct lazy-loading behavior  

Read operations use:

@Transactional(readOnly = true)


which improves performance and JDBC efficiency.

---

# Validation Layer: Purpose & Behavior

## Why Custom Validators Are Needed

Bean Validation (`@NotBlank`, `@Pattern`, etc.) validates only **user input**.  
Business rules require additional logic.

Validators enforce:

- unique ID constraints  
- unique airplane numbers  
- existence of foreign-key references  
- deletion rules based on relationships  
- cross-entity constraints  
- domain consistency rules  

This prevents business logic duplication.

---

## Why StaffValidator Ensures Global Staff ID Uniqueness

Since all staff types share a single table:
```
Staff (parent)
├── AirlineEmployee
└── AirportEmployee
```

IDs must be globally unique.

`StaffValidator.assertStaffIdUnique()` prevents:

- duplicate IDs across different staff types  
- cross-table inconsistencies  
- database uniqueness violations  

---

# DTOs & Mapping Layer

## Why DTOs (Form Objects) Are Used

DTOs ensure:

- controllers never expose entities directly  
- input-level validation  
- safe mapping and transformation  
- protection from invalid fields  

---

## Why We Use Mappers

Centralized mapping provides:

- no repetitive conversion code  
- cleaner controllers  
- isolated transformation logic  
- fewer side effects  

---

# Controller Layer: Role & Behavior

## Why Controllers Use `@Valid` and `BindingResult`

Controllers handle:

- HTTP requests  
- form binding  
- Bean Validation  
- error propagation  
- view rendering  

Errors from:

- Bean Validation  
- Service layer  
- Custom Validators  

are attached to fields or added as global errors.

This ensures proper UI feedback.

---

# Templates (Thymeleaf): Purpose & Behavior

## Why Thymeleaf Is Used

Thymeleaf provides:

- Spring MVC integration  
- statically analyzable templates  
- automatic form binding  
- field-level error reporting via `#fields.hasErrors`  

Perfect for server-rendered CRUD applications.

---

# Error Handling Strategy

## Why Explicit Exceptions Are Used

Validators and services throw:

- `IllegalArgumentException`  
- `IllegalStateException`  

This helps controllers distinguish:

- validation errors  
- business rule violations  
- missing references  

And map them cleanly to UI messages.

---

# Entity Relationships and ORM Mapping

## Why JPA Annotations Are Used for Relationships

Entities mirror the domain model:

- Airplane → Flights  
- AirlineEmployee → FlightAssignments  
- Flight → Tickets  
- NoticeBoard → Flights  

ORM benefits:

- enforced referential integrity  
- no manual JOINs  
- automatic cascading & lazy loading  
- database-level consistency  

---

# Database Connectivity

## Why `application.properties` Controls Hibernate Behavior

Key properties:

- `spring.jpa.hibernate.ddl-auto`  
- `spring.jpa.show-sql`  
- `spring.jpa.properties.hibernate.format_sql`  

These enable:

- schema generation  
- SQL debugging  
- formatted output  
- clean configuration without XML  

---

---

# Sorting & Filtering Architecture

## Purpose of Sorting and Filtering

Sorting and filtering are implemented to allow users to:

- efficiently explore large datasets  
- refine results based on meaningful criteria  
- control result ordering without modifying database state  

All sorting and filtering logic is implemented in a **read-only, non-invasive manner**, preserving data integrity.

---

## Architectural Principles

Sorting and filtering follow these strict rules:

- **Controllers handle request parsing and validation**
- **Services contain the selection logic**
- **Repositories execute optimized database queries**
- **Entities remain unaware of filtering concerns**
- **UI only provides input mechanisms**

This ensures full separation of concerns.

---

## Sorting: Design and Flow

### Why Spring Data `Sort` Is Used

Spring Data `Sort` provides:

- type-safe sorting
- database-level ordering
- no in-memory sorting overhead
- clean integration with `JpaRepository`

Sorting is always delegated to the database using `ORDER BY`.

---

### Sorting Flow (End-to-End)

1. **UI**
   - User selects a sort field and direction
   - Or clicks on a sortable table header

2. **Controller**
   - Receives `sort` and `dir` as request parameters
   - Applies a **whitelist** to prevent invalid fields
   - Constructs a `Sort` object:
     ```java
     Sort.by(direction, sortField)
     ```

3. **Service**
   - Accepts `Sort` as a parameter
   - Forwards it to the repository
   - Provides a fallback sort if `null`

4. **Repository**
   - Executes database-level sorting via Spring Data JPA

---

# Filtering Architecture

## Purpose of Filtering

Filtering allows users to narrow down result sets based on domain-specific criteria without modifying persisted data.

Filtering is optional, composable, and executed at database level.

---

## Filtering Responsibilities by Layer

- **UI**  
  Collects user input through constrained form fields.

- **Controller**  
  Parses and validates filter parameters, converting them into domain-safe types.

- **Service**  
  Decides which filters are active and selects the appropriate query logic.

- **Repository**  
  Executes optimized database queries using Spring Data JPA.

---

# Project Structure Overview
```
src/main/java
├── controller
├── dto
├── mapper
├── model
├── repository
├── service
├── validations

src/main/resources
├── templates
├── application.properties
```

This enforces strict architectural layering and maintainability.
