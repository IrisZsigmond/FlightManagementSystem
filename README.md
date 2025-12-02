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
