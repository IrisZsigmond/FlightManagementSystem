# Repository Implementation

## Why Generics `<T, ID>` Are Used
The base repository is implemented using Java generics `<T, ID>` to make it reusable and type-safe for different entity types.

- `T` = entity type (e.g., Airplane, Luggage, Passenger)
- `ID` = identifier type (e.g., String)

Using generics:
- prevents code duplication
- ensures compile-time type safety
- keeps implementation clean, maintainable, extendable

---

## Why Data Is Stored in a `ConcurrentHashMap`
The in-memory repositories use `ConcurrentHashMap` for storage instead of HashMap or List.

Motivations:
- thread-safe read/write in multi-threaded Spring Boot environment
- avoids race conditions
- prevents data corruption
- internal fine-grained locking

Performance benefits:
- O(1) average access time for get/put/remove
- safe for concurrent HTTP requests
- no null keys/values

---

## Why `Function<T, ID>` Is Used
Repositories receive a `Function<T, ID>` called **idExtractor**, which extracts an entity’s ID.

This allows the generic repository to be independent of entity structure.

---

## Why `final` Is Important
- immutable references
- improves thread safety
- clear design intent
- recommended practice for dependency injection

---

## Why `findById` Returns `Optional`
`Optional` makes absence explicit and prevents:
- null checks
- NullPointerExceptions

Supports functional idioms (`map`, `orElse`, `ifPresent`)

---

## Why `findAll()` Returns a Defensive Copy
`new ArrayList<>(store.values())` protects repository state.

Benefits:
- encapsulation
- thread-safety snapshot
- no accidental external modification

---

## Annotations Used

| Annotation | Purpose |
|---|---|
| `@Repository` | Marks class as data-access component, enables bean detection and exception translation |
| `@Primary` | Selects default implementation when multiple beans exist |

---

## Why Base Repository Does Not Have `@Repository`
`BaseRepositoryInMemory<T,ID>` is generic — Spring cannot instantiate generic beans.

Concrete repositories (e.g. AirplaneRepositoryInMemory) provide:
- concrete type parameters
- idExtractor

---

# Service Layer: Structure & Rationale

The service layer creates a clean boundary between controllers and repositories.

Architecture Pattern:

| Layer | Description |
|---|---|
| BaseService `<T,ID>` | Generic CRUD contract interface |
| BaseServiceImpl `<T,ID>` | Abstract reusable implementation of BaseService |
| Entity Service Interface | Extends BaseService and declares domain-specific logic |
| Entity Service Impl | Extends BaseServiceImpl and implements domain logic |

### Benefits

- no duplicate CRUD logic
- strict layering
- testability
- type-safe
- easy extensibility (new entities are easy to add)
- repository implementation can change without affecting services/controllers

---

# Why GlobalBindingConfig Is Used

Some domain models contain `List<T>`.  
HTML text inputs only send Strings.  
Spring cannot do this conversion automatically.

### Purpose
Use `@ControllerAdvice` and `WebDataBinder` to globally register type converters.

### What it does
These converters transform a comma-separated string like **"A1, A2, A3"** → into a `List<T>` when saving form input, and convert a `List<T>` back into **"A1, A2, A3"** when rendering values back into forms.

### Benefits
- controllers remain clean
- forms stay simple text-based
- models remain properly typed (objects, not strings)
- consistent processing across entire application
