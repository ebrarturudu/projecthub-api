# 2. Package Structure

## Overview

ProjectHub uses a domain-oriented package structure.

The primary organization principle is based on business domains rather than technical layers alone.

This approach keeps related business functionality together while preserving clear architectural boundaries between interfaces, application logic, domain rules, and infrastructure concerns.

Version 1 intentionally uses a simple package structure.

Additional sub-packages will be introduced only when the complexity of a domain requires them.

---

## Package Organization Strategy

ProjectHub follows a domain-oriented (package-by-feature) organization strategy.

Instead of grouping all controllers, services, repositories, and entities into global technical packages, each major business domain owns its related components.

The initial structure follows this pattern:

```text
task/
├── controller/
├── application/
├── domain/
└── infrastructure/

project/
├── controller/
├── application/
├── domain/
└── infrastructure/

board/
├── controller/
├── application/
├── domain/
└── infrastructure/

comment/
├── controller/
├── application/
├── domain/
└── infrastructure/
```
The same structure may be applied to other domains when appropriate.

---

## Why Domain-Oriented Organization?

A domain-oriented structure provides several benefits for ProjectHub.

### Localized Changes

When a feature changes, most of the related code can be found within the corresponding domain.

For example, changes related to Tasks should primarily remain within:

```text
task/
```
rather than being distributed across global:

```text
controller/
service/
repository/
entity/
```

packages.

### Improved Maintainability

Keeping related components together makes the codebase easier to navigate as the number of domains increases.

Developers can focus on a specific business capability without searching across unrelated packages.

### Clearer Domain Boundaries

The package structure reflects the business structure of the application.

For example:

```text
organization/
project/
board/
task/
comment/
invitation/
membership/
```

These packages represent meaningful business concepts rather than only technical concerns.

---
## Domain Package Structure

Each domain may initially contain the following packages:

```text
domain-name/
├── controller/
├── application/
├── domain/
└── infrastructure/
```
Each package has a specific responsibility.

### Controller

The `controller` package contains HTTP-related components.

Responsibilities include:

* Receiving HTTP requests.
* Validating request structure.
* Mapping requests to application inputs.
* Calling application use cases.
* Returning HTTP responses.

Controllers should remain thin and should not contain core business logic.

Example:
```text
task/controller/
└── TaskController
```

---

## Application

The application package contains application use cases and application-level coordination logic.

Responsibilities include:

* Executing application use cases.
* Coordinating domain operations.
* Loading required domain objects.
* Coordinating repository access.
* Managing application workflows.

Examples:

```text
task/application/
├── CreateTaskUseCase
├── AssignTaskUseCase
└── MoveTaskUseCase
```

Additional sub-packages such as `usecase` or `dto` may be introduced when the number of components justifies further organization.

---

## Domain

The `domain` package contains the core business model and rules for the domain.

Responsibilities include:

* Domain entities.
* Value objects.
* Domain-specific enumerations.
* Domain behavior.
* Business invariants.
* Repository abstractions when appropriate.

Example:

```text
task/domain/
├── Task
├── TaskStatus
├── TaskPriority
└── TaskRepository
```

The Domain package must remain independent from infrastructure technologies whenever practical.

---

## Infrastructure

The `infrastructure` package contains technical implementations required by the domain or application.

Responsibilities include:

* Persistence implementations.
* Spring Data JPA integration.
* Database entities or mappings when required.
* External service integrations.
* Other framework-specific implementations.

Example:

```text
task/infrastructure/
└── JpaTaskRepository
```

Additional sub-packages such as `persistence` may be introduced when the infrastructure code grows.

---

## Shared Components

Not every component belongs to a single business domain.

ProjectHub may contain a small shared area for genuinely cross-cutting concerns.

Examples may include:

```text
shared/
├── exception/
├── validation/
├── response/
└── configuration/
```

Shared components should be introduced carefully.

A component should only be placed in `shared` when it is genuinely used across multiple domains and does not belong to a specific business domain.

The `shared` package must not become a general-purpose dumping ground for unrelated code.

---

## Security Package

Security is considered a cross-cutting concern rather than a business domain.

Security-related components may therefore be organized separately:

```text
security/
├── authentication/
├── authorization/
└── jwt/
```

The exact internal structure may evolve during implementation.

Security components should integrate with the application without placing authentication or framework-specific logic inside domain entities.

---

## Initial Package Structure

The initial ProjectHub structure is planned as:

```text
src/main/java/com/projecthub/
│
├── organization/
│   ├── controller/
│   ├── application/
│   ├── domain/
│   └── infrastructure/
│
├── project/
│   ├── controller/
│   ├── application/
│   ├── domain/
│   └── infrastructure/
│
├── board/
│   ├── controller/
│   ├── application/
│   ├── domain/
│   └── infrastructure/
│
├── task/
│   ├── controller/
│   ├── application/
│   ├── domain/
│   └── infrastructure/
│
├── comment/
│   ├── controller/
│   ├── application/
│   ├── domain/
│   └── infrastructure/
│
├── invitation/
│   ├── controller/
│   ├── application/
│   ├── domain/
│   └── infrastructure/
│
├── membership/
│   ├── controller/
│   ├── application/
│   ├── domain/
│   └── infrastructure/
│
├── user/
│   ├── controller/
│   ├── application/
│   ├── domain/
│   └── infrastructure/
│
├── security/
│
└── shared/
```

This structure represents the initial architectural direction rather than a rigid implementation requirement.

---

## Package Growth Strategy

ProjectHub follows an incremental package growth strategy.

The initial implementation should avoid unnecessary package depth.

For example, the following structure is preferred initially:
```text
task/
├── application/
│   └── CreateTaskUseCase
├── domain/
│   ├── Task
│   └── TaskRepository
└── infrastructure/
    └── JpaTaskRepository
```

If the number of components increases significantly, the structure may evolve into:

```text
task/
├── application/
│   ├── usecase/
│   └── dto/
│
├── domain/
│   ├── model/
│   └── repository/
│
├── controller/
│   ├── request/
│   └── response/
│
└── infrastructure/
    └── persistence/
```

This deeper structure should only be introduced when it provides a real organizational benefit.

---

## Architectural Principle

Package structure should support the architecture rather than define it.

The existence of a package does not automatically determine where business logic belongs.

The following responsibility boundaries remain more important than the physical package structure:

```text
Controller
    ↓
Application
    ↓
Domain
    ↓
Infrastructure
```

The package structure may evolve as the system grows, but these architectural boundaries should remain clear.

---

## Version 1 Decision

Version 1 adopts the following package structure strategy:

1. Use domain-oriented (package-by-feature) organization.
2. Group related components under their business domain.
3. Use `controller`, `application`, `domain`, and `infrastructure` as the initial internal structure.
4. Avoid unnecessary nested packages.
5. Introduce deeper sub-packages only when complexity requires them.
6. Keep genuinely cross-cutting components in `shared`.
7. Keep security concerns separate from business domains.
8. Preserve architectural boundaries regardless of package structure changes.

This approach provides a balance between maintainability, scalability, simplicity, and learning value.
