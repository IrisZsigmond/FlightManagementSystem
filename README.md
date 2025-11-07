REPOSITORY IMPLEMENTATION:

1.	Why Generics (<T, ID>) Are Used
The base repository is implemented using Java generics (<T, ID>) to make it reusable and type-safe for different entity types.
o	T represents the entity type (e.g., Airplane, Luggage, Passenger)
o	ID represents the type of the entity’s identifier (e.g., String)
Using generics allows the same repository logic to work for all entities without code duplication. It ensures compile-time type safety, preventing accidental misuse (such as saving a Luggage object into an Airplane repository), while keeping the implementation clean, extensible, and maintainable.
This design makes the in-memory repository flexible and strongly typed — an ideal foundation for both current and future entities in the system.

2.	Why Data Is Stored in a ConcurrentHashMap
The in-memory repositories use a ConcurrentHashMap to store entities instead of a regular HashMap or List structure.
This design choice ensures thread-safe, high-performance data access in a multi-threaded environment such as a Spring Boot application, where multiple HTTP requests can read or modify data at the same time.
ConcurrentHashMap allows concurrent read and write operations without external synchronization. Unlike HashMap, it prevents race conditions and data corruption by managing internal locks on small portions of the data rather than locking the entire map. This provides excellent performance and safety under parallel workloads.
A List-based repository would require iterating through the entire collection to find, update, or delete an element — resulting in O(n) time complexity for these operations. As the number of stored entities grows, performance quickly degrades.
Summary:
In contrast, ConcurrentHashMap provides:
o	O(1) average-time access for CRUD operations (get, put, remove, etc.)
o	Thread safety without explicit synchronization — ideal for concurrent web requests
o	Fine-grained internal locking, allowing multiple threads to operate in parallel
o	No null keys or values, preventing data inconsistencies

3.	Why Function<T, ID> Is Used
The repository constructor receives a Function<T, ID> parameter, called idExtractor, which defines how to obtain the unique identifier from a given entity.
This approach allows the generic repository to remain independent of specific entity structures. Each concrete repository provides its own ID extractor.

4.	Why final It’s Important Here

o	Immutability of critical references
o	Thread-safety improvement
o	Design clarity and intent
o	Good practice for dependency injection

5.	Why Optional Is Used in findById
The findById method returns an Optional<T> instead of null to handle missing entities safely and clearly. Optional is a Java 8+ feature that represents a value that may or may not be present.
o	By returning an Optional, the repository:
o	Eliminates the need for manual null checks
o	Prevents potential NullPointerException errors
o	Makes the absence of a value explicit and intentional
o	Encourages modern, functional-style handling (ifPresent, orElse, map, etc.)

6.	Why findAll() Returns a Defensive Copy
The findAll() method returns a defensive copy of the data (new ArrayList<>(store.values())) rather than the internal collection itself.
This ensures that external code cannot accidentally modify the repository’s internal state.
•	Encapsulation: prevents outside modifications from altering internal data
•	Thread safety: provides a consistent snapshot even during concurrent access
•	Predictable behavior: protects against unintended side effects
•	Data integrity: internal storage remains stable and isolated
This design guarantees that each call to findAll() provides a safe, independent view of the current repository data, preserving both safety and consistency in multi-threaded environments.

7.	Annotations Used
@Repository
Marks the class as a data access component within the Spring framework.
This enables automatic bean detection and registration through component scanning and identifies the class as part of the repository layer.
It also activates Spring’s exception translation, converting low-level data access errors into Spring’s standardized exceptions.
@Primary
Indicates that this repository is the default implementation when multiple beans of the same type exist.
It ensures that Spring injects this in-memory repository by default, even if other implementations (e.g., a database-backed one) are later added.

8.	Why the Base Repository Is Not Annotated with @Repository
The BaseRepositoryInMemory class is generic (<T, ID>) and designed to be extended by concrete repositories for specific entity types.
It cannot be annotated with @Repository because Spring cannot instantiate generic classes with unknown type parameters or constructor dependencies.
Each subclass (e.g., AirplaneRepositoryInMemory, LuggageRepositoryInMemory) provides:
•	Concrete type arguments for T and ID
•	A method reference (idExtractor) defining how to obtain an entity’s ID
This allows Spring to correctly recognize and instantiate only the concrete repository beans, while keeping the base class purely abstract and reusable

Service Layer: Structure and Rationale
The service layer provides a clean boundary between controllers (HTTP/API) and repositories (data access). It is designed around a generic core for common CRUD behavior and thin, entity-focused extensions for domain logic. This keeps the codebase consistent, testable, and easy to evolve.
Architectural Overview
The layer follows a four-piece pattern:
1.	Generic service contract — BaseService<T, ID> defines the canonical CRUD surface (save, find, update, delete, existence checks, counting, clearing). It is purely an interface: no framework annotations and no implementation details.
2.	Abstract generic implementation — BasicServiceImpl<T, ID> provides the default implementation of the BaseService contract once, for all entities. It delegates to a generic repository abstraction and centralizes guardrails such as null checks and “not found” behavior. This class is abstract and uses generics so it can be reused broadly without copy/paste.
3.	Entity-specific service interfaces — e.g., FlightService, AirplaneService, AirlineEmployeeService. Each extends BaseService<T, ID> to inherit CRUD, then declares domain-specific capabilities (for example, “find by airplane id”, “find by role”, “find by capacity”). These interfaces define the public API for each domain area without committing to how it’s implemented.
4.	Entity-specific service implementations — e.g., FlightServiceImpl, AirplaneServiceImpl. Each extends BasicServiceImpl<T, ID> to inherit the shared CRUD implementation and implements its entity interface to provide the domain-specific methods. These are the only service classes marked with @Service, making them Spring-managed beans and the units injected into controllers.
Why this design
•	Eliminates duplication: CRUD logic is implemented once in BasicServiceImpl. All entities benefit from the same correctness, validation, and semantics.
•	Strong separation of concerns: Generic persistence behavior lives in the base implementation; entity rules and queries live in the entity services; HTTP concerns remain in controllers.
•	Type safety with generics: T (entity) and ID (identifier) parameters ensure compile-time safety and a single pattern across the codebase.
•	Swap-friendly repositories: Services depend on a repository abstraction rather than a concrete storage. Moving from in-memory to a database-backed repository later does not affect service or controller code.
•	Maintainability and readability: A predictable, uniform layout (interface → abstract base implementation → entity interface → entity implementation) makes navigation and onboarding straightforward.
•	Testability: Each service can be unit-tested by mocking the repository abstraction. Domain-specific methods remain small and focused, simplifying test scenarios.
Dependency boundaries
Services depend on a repository abstraction that exposes CRUD primitives. BasicServiceImpl holds that dependency and exposes a protected accessor so entity services can perform domain filtering without breaking encapsulation. Controllers depend only on service interfaces, never directly on repositories, reinforcing a clean layering.
Error handling and validation
The base implementation centralizes basic validations (e.g., non-null arguments) and consistent “not found” behavior. Entity services add domain-specific checks (for example, validating search parameters or enforcing ID consistency on updates). This keeps error semantics uniform across resources while allowing each domain to express its rules clearly.
Extensibility
Adding a new entity involves:
•	Creating or reusing a repository implementation.
•	Defining an entity-specific service interface that extends BaseService<T, ID>.
•	Implementing the interface by extending BasicServiceImpl<T, ID> and adding any domain-specific queries.
Because the CRUD implementation is already provided, new services focus almost entirely on domain concerns.
Spring integration
Only the concrete entity service implementations are annotated as beans. The generic interface and abstract base are intentionally not annotated to avoid ambiguous or uninstantiable beans. Constructor injection is used throughout for clarity and testability. If multiple repository implementations exist for the same entity, Spring selection is managed using @Primary or qualifiers at the repository layer, without changing the service API.

WHY GlobalBindingConfig IS USED:
Some domain models contain Lists. HTML text inputs can only send strings. Spring does not know how to do this conversion automatically. Because of this, forms would normally break for all fields where the type is List<SomeModel>. The GlobalBindingConfig fixes this.
How does it work?
It uses Spring’s @ControllerAdvice + WebDataBinder to register type converters once, globally. These converters transform a comma-separated string like “A1, A2, A3” into a List<T> when saving form input, and convert a List<T> back into “A1, A2, A3” when rendering values back into forms.
This centralizes all conversion logic in a single place, so:
•controllers remain clean
•forms can stay text-based
•models can remain correctly typed (objects, not strings)
•all list mappings work consistently across the whole application
