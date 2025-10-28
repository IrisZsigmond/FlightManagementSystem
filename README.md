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
