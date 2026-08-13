# 1. Architecture Overview

## Overview

ProjectHub is designed as a production-inspired backend application rather than a simple CRUD application.

The architecture is designed to provide:

- Clear separation of responsibilities.
- Maintainable and testable business logic.
- Loose coupling between business rules and technical infrastructure.
- A structure that can evolve as the project grows.
- A clear separation between HTTP, application logic, domain rules, and infrastructure concerns.

Version 1 will use a controlled combination of domain-oriented organization and layered architecture.

The architecture may evolve in future versions if the system grows in complexity.

---

## Architectural Approach

ProjectHub uses a hybrid architectural approach combining:

- Domain-oriented organization.
- Layered architecture.
- Selected principles from Clean Architecture.
- Dependency Inversion where it provides practical value.

The project does not aim to implement every aspect of Clean Architecture from the beginning.

The primary goal is to maintain a clear and understandable architecture without introducing unnecessary abstraction or complexity.

---

## Main Architectural Layers

ProjectHub is organized around the following major layers:

```text
Client
   |
   v
Interfaces
   |
   v
Application
   |
   v
Domain
   |
   v
Infrastructure
   |
   v
Database
```

Each layer has a specific responsibility.

---

## Interfaces Layer

The Interfaces layer is responsible for communication between external clients and the application.

In Version 1, the primary interface is the REST API.

Responsibilities include:

* Receiving HTTP requests.
* Validating request structure.
* Mapping HTTP requests to application inputs.
* Calling application use cases.
* Mapping application results to HTTP responses.
* Returning appropriate HTTP status codes.

The Interfaces layer must not contain core business logic.

Example components include:

```text
TaskController
ProjectController
BoardController
CommentController
InvitationController
```

Controllers should remain thin and delegate business operations to the Application layer.

---

## Application Layer

The Application layer coordinates application use cases.

It defines what the system can do and coordinates the steps required to perform those operations.

Examples include:

```text
CreateTask
AssignTask
MoveTask
AddComment
CreateProject
InviteMember
AcceptInvitation
```

Responsibilities include:

* Coordinating use cases.
* Managing application workflows.
* Performing authorization checks where appropriate.
* Loading required domain objects.
* Calling domain behavior.
* Coordinating repositories and other application dependencies.
* Returning results to the Interfaces layer.

The Application layer should not contain HTTP-specific logic.

It should also avoid implementing business rules that belong directly to domain entities.

---

## Domain Layer

The Domain layer contains the core business concepts and rules of ProjectHub.

The Domain layer must remain independent from technical infrastructure whenever practical.

Domain concepts include:

```text
Organization
Project
Board
Task
Comment
User
Membership
Invitation
```

Responsibilities include:

* Representing business entities.
* Representing domain states and behaviors.
* Enforcing business invariants.
* Defining valid domain operations.
* Representing business-related value objects and enumerations when required.

Examples of domain rules include:

* A Project belongs to exactly one Organization.
* A Project has exactly one Board in Version 1.
* A Task belongs to exactly one Project.
* A Task can have zero or one assignee in Version 1.
* A Task can only move through permitted workflow transitions.
* An Invitation cannot be accepted after expiration.
* A Viewer cannot perform Task operations.

Business rules should not depend on controllers, HTTP, PostgreSQL, or other infrastructure details.

---

## Infrastructure Layer

The Infrastructure layer contains technical implementations required by the application.

Responsibilities include:


* Database access.
* Persistence implementations.
* Spring Data JPA integration.
* PostgreSQL integration.
* Authentication and security infrastructure.
* JWT implementation.
* External service integrations when introduced.

Infrastructure-specific technologies may include:

```text
Spring Data JPA
Hibernate
PostgreSQL
Spring Security
JWT
Docker
```

Infrastructure components implement the technical details required by the Application and Domain layers.

The Domain layer should not depend directly on infrastructure technologies.

---

## Dependency Direction

ProjectHub follows the principle that higher-level business logic should not depend directly on lower-level technical details.

The preferred dependency direction is:
```text
Interfaces
    |
    v
Application
    |
    v
Domain


Infrastructure
    |
    +------> implements required abstractions
```

Infrastructure may depend on Application or Domain abstractions where required.

The Domain layer should not depend on Infrastructure.

For example, business logic should not directly depend on:

```text
PostgreSQL
Hibernate
Spring Security
HTTP
```

---

## Repository Responsibility

Repositories provide an abstraction for accessing persisted domain data.

The repository concept is separated from its technical implementation when practical.

For example:

```text
Application / Domain
    |
    v
TaskRepository
  (abstraction)
    ^
    |
JpaTaskRepository
    |
    v
PostgreSQL
```

The repository abstraction represents what the application needs to do with persisted data.

The Infrastructure layer is responsible for the actual database implementation.

Version 1 will avoid unnecessary repository abstractions and additional layers when they do not provide meaningful architectural value.

---

## DTOs

Data Transfer Objects (DTOs) are used to separate external API models from internal domain models.

For example:

```text
HTTP Request
    |
    v
CreateTaskRequest
    |
    v
CreateTaskUseCase
    |
    v
   Task
```

DTOs define the data that an external client is allowed to provide or receive.

Domain entities should not be exposed directly as API request or response models when doing so would create unnecessary coupling.

DTOs also help prevent clients from directly controlling internal fields such as:

* Database identifiers.
* Creation timestamps.
* Internal state.
* System-managed fields.

---

## Domain-Oriented Organization

The source code will be organized primarily around business domains rather than technical layers alone.

For example:

```text
task/
project/
board/
comment/
organization/
invitation/
membership/
```

Within these domains, related components may be grouped according to their architectural responsibility.

The exact package structure may evolve during implementation.

The goal is to keep related business concepts close together while maintaining clear architectural boundaries.

---

## Separation of Responsibilities

Each layer should have a clearly defined responsibility.

```text
Interfaces
    "How does the request enter the system?"

Application
    "What operation does the system perform?"

Domain
    "What are the business rules?"

Infrastructure
    "How are technical operations performed?"
```

This separation prevents a single component from becoming responsible for HTTP handling, business rules, authorization, and database operations simultaneously.

---

## Error Handling

Application and domain operations should communicate failures using appropriate application or domain-level mechanisms.

HTTP-specific error responses should be handled by the Interfaces layer.

For example:

```text
Domain/Application
    |
    v
Business Exception
    |
    v
Global Exception Handler
    |
    v
HTTP Error Response
```

The Domain layer should not be responsible for constructing HTTP responses.

---

## Security and Authorization

Authentication and authorization are treated as separate concerns.

Authentication determines:

```text
Who is the user?
```
Authorization determines:

```text
What is the user allowed to do?
```
Authentication infrastructure will be implemented using Spring Security and JWT.

Authorization decisions will be based on:

* Organization membership.
* Organization role.
* Project membership.
* Project role.
* Resource ownership or assignment where required.
* Project and Organization lifecycle state.

Authorization logic should not be scattered across controllers.

---

## Transaction Management

Application use cases are the primary boundary for transactional operations.

A use case that performs multiple related operations should be treated as a single application operation where appropriate.

For example:

```text
AcceptInvitation
    |
    +--> Validate Invitation
    |
    +--> Create Membership
    |
    +--> Mark Invitation as ACCEPTED
```
These operations should be coordinated consistently to prevent partially completed business operations.

---

## Testing Strategy

The architecture is designed to support testing at multiple levels.

### Domain Tests

Domain behavior and business rules should be testable independently from infrastructure.

Examples:

* Task state transitions.
* Invitation expiration rules.
* Project lifecycle rules.

### Application Tests

Application use cases should be tested independently from HTTP controllers where practical.

Examples:

* CreateTask.
* AssignTask.
* AcceptInvitation.
* AddComment.

### Integration Tests

Integration tests will verify interactions with infrastructure components such as:

* PostgreSQL.
* Spring Data JPA.
* Spring Security.

### API Tests

API-level tests will verify:

* HTTP endpoints.
* Request validation.
* Authentication.
* Authorization.
* HTTP responses.

---
## Version 1 Architectural Scope

Version 1 focuses on establishing a strong and understandable backend foundation.

The following architectural capabilities are included:

* RESTful API.
* Layered application structure.
* Domain-oriented organization.
* Application use cases.
* Domain business rules.
* DTO-based API communication.
* PostgreSQL persistence.
* Spring Data JPA.
* Spring Security.
* JWT authentication.
* Role-based authorization.
* Transaction management.
* Automated testing.

Advanced architectural patterns will only be introduced when they solve an actual problem.

The project intentionally avoids unnecessary complexity during Version 1.

---

## Future Evolution

The architecture should allow ProjectHub to evolve without requiring a complete rewrite.

Potential future architectural extensions include:

* Supporting multiple Boards per Project.
* Supporting multi-assignee Tasks.
* Supporting customizable workflows.
* Introducing audit history and event tracking.
* Introducing advanced notification mechanisms.
* Integrating AI-assisted application capabilities.
* Supporting additional external integrations.
* Supporting more advanced Organization and Project administration.

These capabilities are not part of Version 1 and will only be introduced when justified by actual product requirements.

Future changes should preserve the existing separation of responsibilities and architectural boundaries.

---

## Architectural Principles

ProjectHub follows these principles:

1. Keep business rules independent from technical infrastructure.
2. Keep controllers thin.
3. Keep application use cases explicit.
4. Prefer domain behavior over large procedural services.
5. Avoid unnecessary abstractions.
6. Use dependency inversion when it provides meaningful value.
7. Keep external API models separate from domain models.
8. Keep authorization decisions out of controllers whenever practical.
9. Keep transaction boundaries around application operations.
10. Prefer simple solutions before introducing complex patterns.
11. Allow the architecture to evolve based on real project requirements.
