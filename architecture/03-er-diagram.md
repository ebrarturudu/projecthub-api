# 3. Entity Relationship Diagram

## Overview

This document defines the Version 1 Entity Relationship (ER) model of ProjectHub.

The ER model represents the persistent relationships between the main business entities of the system.

The diagram reflects the business rules and domain constraints defined in the ProjectHub domain documentation.

Version 1 intentionally uses a simplified relational model. Future versions may introduce additional relationships as the product evolves.

---

## Version 1 ER Diagram

```mermaid
erDiagram

    USER {
        id PK
    }

    ORGANIZATION {
        id PK
    }

    ORGANIZATION_MEMBERSHIP {
        id PK
        user_id FK
        organization_id FK
    }

    PROJECT {
        id PK
        organization_id FK
    }

    PROJECT_MEMBERSHIP {
        id PK
        user_id FK
        project_id FK
    }

    BOARD {
        id PK
        project_id FK
    }

    TASK {
        id PK
        project_id FK
        assignee_id FK
    }

    COMMENT {
        id PK
        task_id FK
        author_id FK
    }

    INVITATION {
        id PK
        organization_id FK
        project_id FK
        invited_user_id FK
        invited_by_id FK
    }

    USER ||--o| ORGANIZATION_MEMBERSHIP : has
    ORGANIZATION ||--o{ ORGANIZATION_MEMBERSHIP : contains

    USER ||--o{ PROJECT_MEMBERSHIP : has
    PROJECT ||--o{ PROJECT_MEMBERSHIP : contains

    ORGANIZATION ||--o{ PROJECT : owns

    PROJECT ||--|| BOARD : has

    PROJECT ||--o{ TASK : contains
    USER ||--o{ TASK : assigned_to

    TASK ||--o{ COMMENT : has
    USER ||--o{ COMMENT : writes

    ORGANIZATION ||--o{ INVITATION : contains
    PROJECT ||--o{ INVITATION : targets

    USER ||--o{ INVITATION : receives
    USER ||--o{ INVITATION : creates
```

---

## Relationship Summary

### User and Organization Membership

A User may belong to zero or one Organization in Version 1.

An Organization may have multiple Organization Memberships.

```text
  USER
    │
    │ 0..1
    ▼
ORGANIZATION_MEMBERSHIP
    ▲
    │ N
    │
 ORGANIZATION
```

The `OrganizationMembership` entity represents the relationship between a User and an Organization.

The Organization role is stored in the membership rather than directly on the User.

---

## User and Project Membership

A User may belong to multiple Projects.

A Project may contain multiple Project Memberships.

```text
  USER
    │
    │ 0..N
    ▼
PROJECT_MEMBERSHIP
    ▲
    │ N
    │
  PROJECT
```

The Project role is stored in `ProjectMembership`.

A User may therefore have different roles in different Projects.

For example:

```text
User
├── Project A → MANAGER
├── Project B → DEVELOPER
└── Project C → VIEWER
```

A User cannot have more than one Project Membership for the same Project.

Therefore, the following combination must be unique:

```text
(user_id, project_id)
```

---

## Organization and Project

An Organization may contain zero or more Projects.

Every Project belongs to exactly one Organization.

```text
ORGANIZATION 1 ───── 0..N PROJECT
```

The relationship is represented by:

```text
PROJECT.organization_id
       ↓
ORGANIZATION.id
```

The Foreign Key is therefore stored on the Project side.

---

## Project and Board

Version 1 uses exactly one Board per Project.

```text
PROJECT 1 ───── 1 BOARD
```

The relationship is represented by:

```text
BOARD.project_id
↓
PROJECT.id
```

The `project_id` value must be unique so that a Project cannot have multiple Boards in Version 1.

Multiple Boards per Project are postponed to a future version.

---

## Project and Task

A Project may contain zero or more Tasks.

Every Task belongs to exactly one Project.

```text
PROJECT 1 ───── 0..N TASK
```

The relationship is represented by:

```text
TASK.project_id
    ↓
PROJECT.id
```

A Project may therefore exist without any Tasks.

---

## Task and Assignee

A Task may have zero or one assignee in Version 1.

A User may be assigned to multiple Tasks.

```text
USER 1 ───── 0..N TASK
```

The relationship is represented by:
```text
TASK.assignee_id
    ↓
USER.id
```

The `assignee_id` field is nullable.

Therefore:

```text
Task A → Mehmet
Task B → Ebrar
Task C → NULL
```

are all valid states.

Version 1 does not support multiple assignees.

The assigned User must be a member of the Task's Project.

This requirement is a business rule and is not represented solely by the Foreign Key.

---

## Task and Comment

A Task may contain zero or more Comments.

Every Comment belongs to exactly one Task.

```text
TASK 1 ───── 0..N COMMENT
```

The relationship is represented by:

```text
COMMENT.task_id
    ↓
TASK.id
```

---

## Comment and Author

Every Comment has exactly one author.

A User may create multiple Comments.

```text
USER 1 ───── 0..N COMMENT
```

The relationship is represented by:

```text
COMMENT.author_id
    ↓
USER.id
```

The User's name is not stored directly in the Comment.

The User is identified through its unique identifier.

---

## Organization and Invitation

Every Invitation belongs to exactly one Organization.

An Organization may have multiple Invitations.

```text
ORGANIZATION 1 ───── 0..N INVITATION
```

The relationship is represented by:

```text
INVITATION.organization_id
        ↓
ORGANIZATION.id
```

This relationship exists for both Organization Invitations and Project Invitations.

---

## Project and Invitation

A Project may have multiple Project Invitations.

An Invitation may optionally reference a Project.

```text
PROJECT 1 ───── 0..N INVITATION
```

The relationship is represented by:

```text
INVITATION.project_id
↓
PROJECT.id
```

The `project_id` field is nullable.

Therefore:

### Organization Invitation

```text
organization_id = ABC
project_id = NULL
```

### Project Invitation

```text
organization_id = ABC
project_id = Project A
```
If an Invitation references a Project, that Project must belong to the same Organization referenced by the Invitation.

This is a business rule.

---

## Invitation and Invited User

An Invitation identifies the User who is being invited.

A User may receive multiple Invitations over time.

```text
USER 1 ───── 0..N INVITATION
```

The relationship is represented by:

```text
INVITATION.invited_user_id
    ↓
USER.id
```

The invited User is therefore identified by User ID rather than by name or other mutable information.

Version 1 prevents multiple pending Invitations for the same User and the same Organization or Project.

---

## Invitation and Inviting User

An Invitation also records the User who created the Invitation.

A User may create multiple Invitations.

```text
USER 1 ───── 0..N INVITATION
```

The relationship is represented by:

```text
INVITATION.invited_by_id
↓
USER.id
```

The same `USER` entity is therefore referenced twice by the Invitation entity, but the relationships have different meanings:

```text
invited_user_id
→ Who is being invited?


invited_by_id
→ Who created the invitation?
```
---

## Foreign Key Strategy

Foreign Keys are used to represent relationships between persistent entities.

A Foreign Key references the primary key of another entity.

For example:

```text
PROJECT.organization_id
↓
ORGANIZATION.id
```

means that every Project references an existing Organization.

The main Version 1 Foreign Keys are:

| Entity | Foreign Key | References |
| :--- | :--- | :--- |
| OrganizationMembership | `user_id` | User |
| OrganizationMembership | `organization_id` | Organization |
| Project | `organization_id` | Organization |
| ProjectMembership | `user_id` | User |
| ProjectMembership | `project_id` | Project |
| Board | `project_id` | Project |
| Task | `project_id` | Project |
| Task | `assignee_id` | User |
| Comment | `task_id` | Task |
| Comment | `author_id` | User |
| Invitation | `organization_id` | Organization |
| Invitation | `project_id` | Project |
| Invitation | `invited_user_id` | User |
| Invitation | `invited_by_id` | User |

---

## Business Rules Not Represented Solely by Foreign Keys

A Foreign Key guarantees that a referenced record exists.

It does not automatically guarantee every business rule of ProjectHub.

For example:

```text
TASK.assignee_id → USER.id
```

guarantees that the assigned User exists.

It does not automatically guarantee that the User is a member of the Task's Project.

Therefore, additional business rules must be enforced by the application and domain logic.

Important examples include:

* A Task assignee must belong to the Task's Project.
* A Project Invitation must reference a Project belonging to the same Organization.
* A User must belong to an Organization before becoming a member of one of its Projects.
* Organization and Project role permissions must be respected when performing operations.
* Only authorized users may create or modify Invitations.
* Viewer users cannot perform Task operations.

These rules are intentionally kept separate from simple relational references.

---

## Version 1 Relationship Constraints

The following constraints are intentionally enforced in the Version 1 model:

* One User may belong to only one Organization.
* One Organization may contain multiple Organization Memberships.
* One User may have only one Project Membership within a specific Project.
* One Project belongs to exactly one Organization.
* One Project has exactly one Board.
* One Task belongs to exactly one Project.
* One Task has zero or one assignee.
* One Task may have zero or more Comments.
* One Comment belongs to exactly one Task.
* One Comment has exactly one author.
* One User cannot have multiple PENDING Organization Invitations for the same Organization.
* One User cannot have multiple PENDING Project Invitations for the same Project.
* A User must belong to an Organization before becoming a member of one of its Projects.
* Organization roles and Project roles are independent.
* Invitation acceptance creates the corresponding Membership.
* Viewer users have read-only access and cannot perform Task operations.

---

## Relationships Intentionally Not Modeled in Version 1

The following relationships are intentionally excluded from the Version 1 model.

### **Board → Task**

Tasks are not directly associated with a Board.

Task status is stored on the Task itself.

The Board represents the project's task visualization rather than owning individual Task records.

---

### **Task → Multiple Assignees**

A Task has at most one assignee in Version 1.

A separate assignment relationship is therefore not required.

Multi-assignee Tasks are postponed to a future version.

---

### **Project → Multiple Boards**

A Project has exactly one Board in Version 1.

Multiple Boards per Project are postponed to a future version.

---

## Version 1 Architectural Decision

The Version 1 ER model intentionally separates:

```text
User
    ↓
Membership
    ↓
Organization / Project
```

from:

```text
Task
    ↓
Assignee
    ↓
User
```

Memberships represent **participation and role within a scope**, while direct User references represent **specific actors involved in a business operation.**

This distinction prevents User roles from becoming tightly coupled to individual entities and allows the same User to participate in different contexts with different responsibilities.

The ER model should evolve together with the domain model and documented architectural decisions.

Future schema changes must preserve existing business invariants unless those invariants are intentionally changed as part of a new product version.

