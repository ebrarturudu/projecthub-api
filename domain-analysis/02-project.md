---

# 2. Project

## Overview

A **Project** represents a specific software product, service, or initiative managed within an Organization.

Projects provide an isolated workspace where members collaborate by organizing tasks, managing workflows, and tracking progress toward a common goal.

Every Project belongs to exactly one Organization and cannot exist independently.

Projects are independent from one another. Membership, roles, tasks, and boards are managed separately for each Project.

---

## Responsibilities

The Project domain is responsible for:

* Providing a dedicated workspace for collaboration.
* Managing Project members and their roles.
* Organizing work through Boards and Tasks.
* Defining Project-specific permissions.
* Managing the Project lifecycle.
* Serving as the parent entity for Boards, Tasks, and future Project resources.

---

## Membership

A Project contains only the members explicitly assigned to it.

Being a member of an Organization does not automatically grant access to every Project within that Organization.

A single user may participate in multiple Projects simultaneously, potentially with different responsibilities.

Project membership is managed by authorized users according to the system's role-based access rules.

---

## Lifecycle

A Project may exist in one of the following states:

* **DRAFT**
* **ACTIVE**
* **ARCHIVED**

### DRAFT

The Project has been created but is still being prepared.

During this stage:

* Members may be added.
* Roles may be assigned.
* Project information may be updated.
* Tasks cannot be created.

### ACTIVE

The Project is actively being developed.

During this stage:

* Tasks may be created and managed.
* Members collaborate on project activities.
* Boards and workflows become operational.

### ARCHIVED

The Project is no longer under active development.

Archived Projects become read-only.

Historical information remains accessible, but no new business operations can be performed.

Projects may be restored from the Archived state if necessary.

---

## Core Attributes

The Project contains the following business information:

* Name
* Project Key
* Description (optional)
* Status
* Start Date (optional)
* Target End Date (optional)
* Created At
* Updated At
* Archived At (optional)

Technical implementation details such as database identifiers are intentionally omitted from this document.

---

## Business Rules

The following business rules apply to the Project domain:

* Every Project belongs to exactly one Organization.
* Only Organization Owners and Organization Admins may create Projects.
* Every Project must have a unique name within its Organization.
* Every Project must have a unique Project Key.
* A Project starts in the **DRAFT** state.
* Tasks cannot be created while the Project is in the **DRAFT** state.
* Project members must be selected from existing Organization members.
* A user may participate in multiple Projects.
* Project roles are assigned independently for each Project.
* Project Keys cannot be modified after creation.
* Archived Projects become read-only.
* Archived Projects may be restored when necessary.