# 8. Domain Relationships

## Overview

This document defines the relationships between the core ProjectHub domains.

The purpose of this document is to provide a clear overview of how Organizations, Users, Projects, Boards, Tasks, Comments, Memberships, and Invitations interact with each other.

These relationships represent business rules and domain constraints rather than technical database implementation details.

---

## Organization ↔ Project

An Organization may contain multiple Projects.

A Project belongs to exactly one Organization.

```text
Organization
├── Project A
├── Project B
└── Project C
```
### Rules
* An Organization may have zero or more Projects.
* A Project must belong to exactly one Organization.
* Projects are isolated from one another.
* A Project cannot exist independently from an Organization.
* A Project cannot be moved between Organizations in Version 1.
* Archiving a Project does not archive its Organization.
* Archiving an Organization makes its Projects read-only.
* A Project may be archived independently while its Organization remains active.

---
## User ↔ Organization Membership

A User participates in an Organization through an Organization Membership.
```text
User
│
└── Organization Membership
│
└── Organization
```

### Rules
* A User may exist without belonging to an Organization.
* A User may belong to only one Organization in Version 1.
* A User's Organization role is defined by the Organization Membership.
* Organization roles in Version 1 are OWNER and ADMIN.
* A User does not automatically gain Project access through Organization membership.
* Leaving an Organization removes the User's Project memberships within that Organization.
* Leaving an Organization does not delete the User account.

---

## User ↔ Project Membership

A User participates in a Project through a Project Membership.

```text
User
│
└── Project Membership
│
└── Project
```

### Rules
* A User must belong to the Project's Organization before becoming a Project member.
* A User may participate in multiple Projects within the same Organization.
* A User may have different Project roles across different Projects.
* A User can have only one Project Membership within a specific Project.
* A Project Membership defines the User's Project role.
* A Project role may be changed without creating a new Membership.
* Leaving a Project removes only the User's membership in that Project.
* Leaving a Project does not remove the User from the Organization.

---

## Organization ↔ Organization Membership

An Organization has members through Organization Memberships.
```text
Organization
├── Membership → User A (OWNER)
├── Membership → User B (ADMIN)
└── Membership → User C (ADMIN)
```

### Rules
* An Organization must always have exactly one Owner.
* An Organization may have multiple Admins.
* Ownership cannot be assigned through a normal invitation.
* Ownership may only be transferred through the defined ownership process.
* Organization membership is independent from Project membership.

---

## Project ↔ Project Membership

A Project has members through Project Memberships.
```text
Project
├── Membership → User A (MANAGER)
├── Membership → User B (DEVELOPER)
└── Membership → User C (VIEWER)
```

### Rules
* A Project may have zero or more members.
* A User may have only one Project Membership within a specific Project.
* A Project Membership assigns exactly one Project role.
* Version 1 Project roles are MANAGER, DEVELOPER, and VIEWER.
* Organization Admin status does not automatically grant Project Manager status.
* Project membership must be explicitly established.

---

## Project ↔ Board

In Version 1, every Project has exactly one Board.
```text
Project
    │
    └── Board
```

### Rules
* Every Project must have exactly one Board in Version 1.
* A Board belongs to exactly one Project.
* The default Board is created automatically when a Project is created.
* A Project cannot have multiple Boards in Version 1.
* Board archiving is not supported in Version 1.
* When a Project becomes read-only, its Board also becomes read-only.
* Multiple Boards per Project are postponed to a future version.

---

## Project ↔ Task

A Task belongs to exactly one Project.

```text
Project
├── Task A
├── Task B
└── Task C
```

### Rules
* A Project may contain zero or more Tasks.
* Every Task must belong to exactly one Project.
* A Task cannot exist independently from a Project.
* A Task cannot belong to multiple Projects.
* Tasks cannot be created while the Project is in the DRAFT state.
* Tasks become read-only when their parent Project becomes read-only.

---

## Board ↔ Task

The Board provides the workflow representation of Tasks within a Project.

A Task belongs to a Project and has a workflow state represented on that Project's Board.
```text
Project
    │
    ├── Board
    │    ├── TODO
    │    ├── IN_PROGRESS
    │    ├── IN_REVIEW
    │    └── DONE
    │
    └── Tasks
```

### Rules
* Every Task has a workflow state in Version 1.
* The workflow state determines the Task's position on the Project Board.
* A Task is not assigned to a separate Board.
* A Task cannot move between different Boards in Version 1.
* Board columns represent workflow states rather than separate Task entities.
* The predefined workflow states in Version 1 are TODO, IN_PROGRESS, IN_REVIEW, and DONE.
* Multiple Boards and customizable workflows are postponed to a future version.

---

## Task ↔ User

A Task may be associated with Users in different roles.
```text
Task
├── Created By → User
└── Assigned To → User
```

### Created By

The Created By relationship identifies the User who created the Task.

The creator and assignee do not have to be the same person.

### Assigned To

The Assigned To relationship identifies the User responsible for completing the Task.

### Rules
* A Task may be unassigned.
* A Task may have zero or one assignee in Version 1.
* A Task cannot have multiple assignees in Version 1.
* MANAGER and DEVELOPER Project members may be assigned Tasks.
* VIEWER members cannot be assigned Tasks.
* A User must be an eligible member of the Project to be assigned a Task.
* Organization OWNER or ADMIN status alone does not grant the ability to be assigned a Task.
* An Organization Owner or Admin may be assigned a Task when they are also an eligible member of the Project.
* The Task creator and assignee may be different Users.
* Multi-assignee Tasks are postponed to a future version.

---

## Task ↔ Comment

A Comment belongs to exactly one Task.

A Task may contain zero or more Comments.
```text
Task
├── Comment 1
├── Comment 2
└── Comment 3
```

A Task may also have no Comments.
```text
Task
└── No Comments
```

### Rules
* Every Comment must belong to exactly one Task.
* A Comment cannot exist independently from a Task.
* A Task may contain zero or more Comments.
* Comments are used for task-specific communication and context.
* Comment history is postponed to a future version.
* Comment modification rules are defined within the Comment domain.

---

## User ↔ Invitation

An Invitation targets a User or allows a registered user to establish membership through an invitation code.

An Invitation does not represent membership itself.
```text
Invitation
    │
    │ ACCEPT
    ↓
Membership
```

### Rules
* An Invitation is a temporary business object used to establish membership.
* An Invitation does not grant access before it is accepted.
* An accepted Invitation results in the creation of the corresponding Membership.
* Accepted Invitations are retained for historical purposes.
* An Invitation can only be accepted once.
* Pending Invitations may have their assigned role changed by authorized users.

---

## Organization ↔ Invitation

An Organization Invitation is used to establish Organization Membership.
```text
Organization
    │
    └── Invitation
        │
        │ ACCEPT
        ↓
Organization Membership
```

### Rules

* An Organization Invitation belongs to exactly one Organization.
* An Organization Invitation may assign the ADMIN role or establish standard Organization membership.
* OWNER cannot be assigned through an Invitation.
* The Organization Owner may invite Admins and standard Organization members.
* Organization Admins may invite standard Organization members.
* An Organization Admin cannot invite another Admin.

---

## Project ↔ Invitation

A Project Invitation is used to establish Project Membership.

```text
Project
    │
    └── Invitation
        │
        │ ACCEPT
        ↓
Project Membership
```

### Rules
* A Project Invitation belongs to exactly one Project.
* A Project Invitation must reference the Project's Organization.
* A Project Invitation may assign MANAGER, DEVELOPER, or VIEWER.
* The invited User must belong to the Project's Organization before the invitation can be accepted.
* A User who is already a Project member cannot receive a new Invitation for that Project.
* The same User cannot have multiple PENDING Invitations for the same Project.
* A PENDING Invitation may have its assigned role changed by an authorized user.
* An accepted Invitation creates a Project Membership.

---

## Invitation ↔ Membership

An Invitation and Membership represent different stages of the access process.
```text
Invitation
    │
    │ PENDING
    │
    │ ACCEPT
    ↓
Membership Created
    │
    ↓
Invitation → ACCEPTED
```

### Rules
* An Invitation does not automatically create Membership when it is created.
* Membership is created only after successful invitation acceptance.
* An accepted Organization Invitation creates an Organization Membership.
* An accepted Project Invitation creates a Project Membership.
* An Invitation cannot be accepted more than once.
* ACCEPTED, EXPIRED, and REVOKED Invitations cannot be modified.
* Invitation records remain available for historical purposes.

---

## Domain Relationship Summary

The primary ProjectHub domain relationships can be summarized as follows:
```text
Organization
│
├── Organization Membership
│       └── User
│
├── Invitation
│       └── Organization Membership
│
└── Project
    │
    ├── Project Membership
    │       └── User
    │
    ├── Invitation
    │       └── Project Membership
    │
    ├── Board
    │       └── Workflow States
    │
    └── Task
    ├── Created By → User
    ├── Assigned To → User
    └── Comment
```
---

## Version 1 Relationship Constraints

Version 1 intentionally uses a simplified domain model.

The following constraints apply:

* One User may belong to only one Organization.
* One Organization must have exactly one Owner.
* One User may have only one Project Membership within a specific Project.
* One Project belongs to exactly one Organization.
* One Project has exactly one Board.
* One Task belongs to exactly one Project.
* One Task has zero or one assignee.
* One Task may have zero or more Comments.
* One Comment belongs to exactly one Task.
* One User cannot have multiple PENDING Organization Invitations for the same Organization.
* One User cannot have multiple PENDING Project Invitations for the same Project.
* A User must belong to an Organization before becoming a member of one of its Projects.
* Organization roles and Project roles are independent.
* Invitation acceptance creates the corresponding Membership.
* Viewer users have read-only access and cannot perform Task operations.

