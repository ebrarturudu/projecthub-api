# 4. Task

## Overview

A **Task** represents a single, trackable piece of work that needs to be completed within a Project.

A Task may represent a small implementation detail or a larger piece of work, depending on the project's needs.

Every Task belongs to exactly one Project.

Tasks are managed through the Project's Board and move through predefined workflow states during their lifecycle.

---

## Responsibilities

The Task domain is responsible for:

* Representing work that needs to be completed.
* Tracking the current workflow state of the work.
* Assigning responsibility for the work to a Project member.
* Managing task priority and deadlines.
* Providing task descriptions and relevant information.
* Supporting task comments.
* Maintaining task history.
* Supporting task reopening when necessary.
* Preserving task data through soft deletion.

---

## Project Relationship

Every Task belongs to exactly one Project.

A Task cannot exist independently from a Project.

A Project may contain multiple Tasks.

The relationship can be represented as:

```text
Organization
    │
    └── Project
          │
          ├── Task
          ├── Task
          └── Task
```
A Task cannot belong to multiple Projects simultaneously.

---

## Board Relationship

Tasks are visualized and organized through the Board belonging to their Project.

A Task is not assigned to a separate Board.

Instead, the Task's current workflow state determines the Board column in which the Task is displayed.

For example:
```text
Project
└── Board
    ├── TODO
    │   └── Task A
    │
    ├── IN_PROGRESS
    │   └── Task B
    │
    ├── IN_REVIEW
    │   └── Task C
    │
    └── DONE
        └── Task D
```
Changing a Task's workflow state changes its position on the Board.

Detailed Board behavior is defined in the Board domain.

---

## Task Key

Each Task has a human-readable Task Key.

The Task Key is derived from the Project Key and a unique numeric identifier.

For example:
```text
Project Key: ECOM

Task Keys:
ECOM-1
ECOM-2
ECOM-3
```

The Task Key provides an easy way for users to identify and reference Tasks.

The Task Key cannot be manually assigned by users and cannot be modified after Task creation.

A separate technical database identifier may be used internally.

---

## Task Creation

Tasks may be created by:

- Organization Owner
- Project Admin
- Project Manager

Developers and Viewers cannot create Tasks in Version 1.

Tasks may be created without an assigned member.

An unassigned Task has no current Assignee and may be assigned later.

For example:
```text
Task:
Implement payment API

Assignee:
UNASSIGNED
```
---

## Assignment

A Task may be assigned to a single Project member.

Multi-assignee Tasks are not supported in Version 1.

A Task may remain unassigned until an appropriate Project member is selected.

The Assignee may be a Project member with an appropriate role and permissions.

Multi-assignee support is postponed to a future version.

---

## Workflow Status

Version 1 uses the following predefined Task workflow states:

- TODO
- IN_PROGRESS
- IN_REVIEW
- DONE

The standard workflow is:
```text
TODO
  ↓
IN_PROGRESS
  ↓
IN_REVIEW
  ↓
DONE
```

# TODO

The Task has been created but work has not started.

# IN_PROGRESS

The assigned member is actively working on the Task.

# IN_REVIEW

The implementation has been completed and the Task has been submitted for review.

# DONE

The Task has been reviewed and completed by an authorized Project Manager.

Detailed workflow transition permissions are defined through the authorization and Board rules.

---

## Task Reopening

A completed Task may be reopened when additional work is required.

For example:
```text
DONE
  ↓
IN_PROGRESS
```

Reopening a Task may be performed by:

- Organization Owner
- Project Admin
- Project Manager

A reopened Task becomes active again and may continue through the normal workflow.

---

## Priority

Each Task has a priority level.

Version 1 supports the following priority levels:

- LOW
- MEDIUM
- HIGH
- URGENT

Priority may be modified by:

- Organization Owner
- Project Admin
- Project Manager

Priority is independent from the Task workflow status.

For example:
```text
Status: IN_PROGRESS
Priority: URGENT
```
---

## Due Date

A Task may have an optional Due Date.

The Due Date represents the expected completion date of the Task.

The Due Date may be modified by:

- Organization Owner
- Project Admin
- Project Manager

If a Task passes its Due Date, its workflow status does not automatically change.

For example:
```text
Status: IN_PROGRESS
Due Date: 2026-08-10
Current Date: 2026-08-13
```

The Task remains **IN_PROGRESS** but may be identified as overdue by the system.

**OVERDUE is not a workflow status.**

It is a derived condition based on the Due Date and the current Task state.

---

## Description

A Task may contain a detailed description explaining the work that needs to be completed.

The description may be modified while the Task is active.

When a Task reaches **DONE**, its description becomes locked.

If the Task is reopened, the description may be modified again.

This prevents completed Tasks from being silently modified long after completion while still allowing necessary changes when additional work is required.

---

## Comments

Tasks may contain multiple comments.

Comments are associated with the Task on which they were created.

For example:
```text
Task
├── Comment
├── Comment
└── Comment
```

Comments are intended to support collaboration and communication between Project members.

Detailed Comment rules are defined in the Comment domain.

---

## Task History

Version 1 maintains a history of important Task changes.

The history may include events such as:

- Task creation
- Task assignment
- Assignee change
- Status change
- Priority change
- Due Date change
- Description change
- Task reopening
- Task completion

Task history provides traceability for important changes made throughout the Task lifecycle.

Detailed audit and activity logging behavior may be expanded in a future version.

---

## Soft Deletion

Tasks use soft deletion rather than immediate physical deletion.

When a Task is deleted, it is marked as deleted and is excluded from normal active Task queries.

The underlying Task data remains available for historical and auditing purposes.

This approach helps preserve Task history and prevents accidental permanent data loss.

---

## Core Attributes

The Task contains the following business information:

- Task Key
- Title
- Description
- Status
- Priority
- Assignee (optional)
- Due Date (optional)
- Project
- Created At
- Updated At
- Deleted At (optional)

Technical implementation details such as database identifiers are intentionally omitted from this document.

---

## Business Rules

The following business rules apply to the Task domain:

- Every Task must belong to exactly one Project.
- A Project may contain multiple Tasks.
- A Task cannot belong to multiple Projects.
- Only Organization Owners, Project Admins, and Project Managers may create Tasks.
- Developers cannot create Tasks in Version 1.
- Viewers cannot create Tasks.
- A Task may remain unassigned.
- A Task may have only one Assignee in Version 1.
- Multi-assignee Tasks are postponed to a future version.
- Task workflow states are TODO, IN_PROGRESS, IN_REVIEW, and DONE.
- A completed Task may be reopened when additional work is required.
- Only Organization Owners, Project Admins, and Project Managers may reopen a completed Task.
- Task Priority may be modified by Organization Owners, Project Admins, and Project Managers.
- Task Due Date may be modified by Organization Owners, Project Admins, and Project Managers.
- OVERDUE is not a Task workflow state.
- A Task's Description becomes locked when the Task reaches DONE.
- A reopened Task may have its Description modified again.
- Task history is maintained for important Task changes.
- Tasks are soft deleted rather than physically deleted.
- A deleted Task is excluded from normal active Task queries.
- Task Key is unique within the Project and cannot be modified after creation.
- Task Key is generated from the Project Key and a unique identifier.
- Task Dependencies are not supported in Version 1.
- Task Attachments are not supported in Version 1.