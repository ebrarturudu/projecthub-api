# 3. Board

## Overview

A **Board** is a visual workspace used to organize and track Tasks within a Project according to their current workflow state.

A Board belongs to exactly one Project and provides a visual representation of the work being performed within that Project.

In Version 1, each Project has exactly one Board.

The Board does not represent a separate Project or a sub-project. It is a mechanism for visualizing and managing the workflow of Tasks within a Project.

---

## Responsibilities

The Board domain is responsible for:

* Providing a visual workspace for Project Tasks.
* Organizing Tasks according to their workflow state.
* Providing predefined workflow columns.
* Allowing authorized users to manage the Board.
* Supporting Task status transitions through Board columns.

---

## Board Creation

A default Board is automatically created when a Project is created.

The default Board contains the following predefined columns:

* **TODO**
* **IN_PROGRESS**
* **IN_REVIEW**
* **DONE**

No additional Board creation or column configuration is required during the initial Project setup.

This approach keeps the Version 1 workflow simple and allows users to begin managing Tasks immediately.

---

## Board Structure

The Board follows the following hierarchy:

```text
Project
└── Board
    ├── TODO
    │   └── Tasks
    ├── IN_PROGRESS
    │   └── Tasks
    ├── IN_REVIEW
    │   └── Tasks
    └── DONE
        └── Tasks
```

A Board contains multiple Columns.

Each Column represents a workflow state.

Tasks are associated with a Column through their current workflow state.

A Task is not assigned to a separate Board. Multiple Tasks can exist within the same Column.

---

## Workflow Columns

Version 1 uses the following predefined workflow columns:

### TODO

The Task has been created but work has not started.

### IN_PROGRESS

The Developer is actively working on the Task.

### IN_REVIEW

The Developer has completed the implementation and submitted the Task for review.

### DONE

The Task has been reviewed and completed by an authorized Project Manager.

The workflow is intentionally fixed in Version 1.

Custom columns and customizable workflows may be introduced in a future version.

---

## Board Permissions

Board management is restricted according to the Project hierarchy.

### Owner

The Organization Owner has full access to the Board.

### Project Admin

The Project Admin may manage the Board within the Project.

### Project Manager

The Project Manager may use the Board to manage Project Tasks but cannot modify the Board structure in Version 1.

### Developer

The Developer may view the Board and move their own assigned Tasks according to the permitted workflow transitions.

### Viewer

The Viewer has read-only access to the Board.

---

## Task Movement

Tasks are moved between Board columns by changing their workflow state.

For example:

```text
TODO
  ↓
IN_PROGRESS
  ↓
IN_REVIEW
  ↓
DONE
```

A Developer may move an assigned Task from **TODO** to **IN_PROGRESS** and from **IN_PROGRESS** to **IN_REVIEW**.

A Project Manager may move a Task from **IN_REVIEW** to **DONE**.

Detailed Task state transition rules are defined within the Task domain.

---

## Lifecycle

Board archiving is not supported in Version 1.

When the parent Project is archived, the Board becomes read-only as a consequence of the Project's archived state.

Board archiving and restoration may be introduced in a future version if multiple Boards per Project are supported.

---

## Core Attributes

The Board contains the following business information:

* Name
* Project
* Created At
* Updated At

Technical implementation details such as database identifiers are intentionally omitted from this document.

---

## Business Rules

The following business rules apply to the Board domain:

* Every Project must have exactly one Board in Version 1.
* A Board belongs to exactly one Project.
* A default Board is automatically created when a Project is created.
* A Board contains four predefined columns in Version 1.
* The predefined columns are TODO, IN_PROGRESS, IN_REVIEW, and DONE.
* Developers cannot create or modify Boards.
* Project Managers cannot create or modify Board structure in Version 1.
* The Organization Owner may manage the Board.
* The Project Admin may manage the Board.
* Viewers have read-only access to the Board.
* Tasks are organized within Board columns according to their workflow state.
* A Task cannot belong to multiple Boards in Version 1.
* Board archiving is not supported in Version 1.
* An archived Project makes its Board read-only.
* Multiple Boards per Project are postponed to a future version.
* Customizable Board columns and workflows are postponed to a future version.
