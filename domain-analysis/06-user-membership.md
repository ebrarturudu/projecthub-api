# 6. User, Membership and Roles

## Overview

A **User** represents an individual account registered in ProjectHub.

A User is independent from Organizations and Projects. The User account represents the identity of a person within the platform, while membership determines the Organizations and Projects that the user can access.

In Version 1, a User may belong to only one Organization.

A User's role is determined by the context in which the User participates:

- Organization membership determines the user's Organization-level role.
- Project membership determines the user's Project-level role.

Roles are therefore assigned to memberships rather than being permanent properties of a User.

---

## User

The User domain is responsible for:

* Representing a registered ProjectHub account.
* Managing user identity and account information.
* Providing the identity used for authentication.
* Participating in Organizations through Organization Membership.
* Participating in Projects through Project Membership.

A User may exist without belonging to an Organization.

Creating a ProjectHub account does not automatically make the User an Organization Owner.

A User becomes an Organization Owner only when they create an Organization.

---

## Organization Membership

An **Organization Membership** represents the relationship between a User and an Organization.

In Version 1, a User can belong to only one Organization.

Organization Membership determines the user's role and access at the Organization level.

The available Organization roles in Version 1 are:

* **OWNER**
* **ADMIN**

### OWNER

The Owner is the highest authority within an Organization.

The Owner:

* Has full access to the Organization.
* Can manage Organization members.
* Can create Projects.
* Can manage Organization-level settings and resources.
* Cannot abandon ownership without transferring ownership to another member.

An Organization must always have exactly one Owner.

### ADMIN

The Admin has Organization-level administrative privileges.

An Admin:

* Can access the Organization's Projects.
* Can manage Organization members according to the defined permissions.
* Can create Projects.
* Can perform Organization-level administrative operations.

Being an Organization Admin does not automatically make the user a Manager of every Project.

Project responsibilities are determined separately through Project Membership.

---

## Project Membership

A **Project Membership** represents the relationship between a User and a Project.

A User must already be a member of the Organization before being added to one of its Projects.

Being a member of an Organization does not automatically grant access to every Project within that Organization.

Project membership must be explicitly assigned.

A User may participate in multiple Projects within the same Organization.

A User may have a different Project role in each Project.

For example:

```text
User: Mehmet

Project A → MANAGER
Project B → DEVELOPER
Project C → VIEWER
```

Only one Project Role can be assigned to a User within a specific Project.

---

## Project Roles

Version 1 uses the following Project roles:

- MANAGER
- DEVELOPER
- VIEWER

### MANAGER

The Manager is responsible for the operational management of a Project.

A Manager may:

- Manage Project Tasks.
- Create and assign Tasks.
- Manage Task priorities and deadlines.
- Manage the Project workflow.
- Manage Project members according to the defined permissions.
- Review completed Tasks.
- Move Tasks through the appropriate workflow states.

The Manager is the primary operational authority within a Project.

### DEVELOPER

The Developer is responsible for implementing assigned work.

A Developer may:

- View the Project and its Board.
- View Tasks available to them.
- Work on assigned Tasks.
- Move their assigned Tasks through permitted workflow states.
- Add Comments to accessible Tasks.

A Developer cannot manage Project membership or Project-level permissions.

### VIEWER

The Viewer has read-only access to a Project.

A Viewer may:

- View Project information.
- View the Project Board.
- View Tasks and their information.
- View Comments.

A Viewer cannot modify Project resources.

---

## Membership Hierarchy

The relationship between Organization and Project access follows this structure:

```text
Organization
│
├── OWNER
├── ADMIN
│
└── Projects
    │
    ├── MANAGER
    ├── DEVELOPER
    └── VIEWER
```

Organization roles and Project roles are independent.

An Organization Admin does not automatically become a Project Manager.

A User must be explicitly assigned a Project Role when participating in a Project.

---

## Membership Rules

The following rules apply to User and Membership domains:

- A User may exist without belonging to an Organization.
- A User may belong to only one Organization in Version 1.
- A User becomes an Owner when creating an Organization.
- An Organization must have exactly one Owner.
- Organization membership is established through the defined invitation process.
- A Project member must already belong to the Project's Organization.
- Organization membership does not automatically grant Project membership.
- Project membership must be explicitly assigned.
- A User may participate in multiple Projects within the same Organization.
- A User may have different Project roles across different Projects.
- A User can have only one Project Role within a specific Project.
- Organization Admin status does not automatically grant Project Manager status.
- Project membership cannot exist independently from Organization membership.
- When a User leaves an Organization, their Project memberships within that Organization are removed.