# 5. Comment

## Overview

A **Comment** represents a piece of communication or additional context related to a specific Task.

Comments allow Project members to communicate about the work being performed on a Task without changing the Task's primary definition.

A Comment belongs to exactly one Task.

Comments are not used to define the work that needs to be completed. The Task Description is responsible for defining the required work, while Comments are used to communicate about the Task during its lifecycle.

---

## Responsibilities

The Comment domain is responsible for:

* Providing communication related to a Task.
* Allowing Project members to provide additional context about a Task.
* Supporting collaboration during the Task lifecycle.
* Allowing users to update their own comments within a limited time period.
* Preserving deleted comments through soft deletion.

---

## Task Relationship

Every Comment belongs to exactly one Task.

A Comment cannot exist independently from a Task.

A Task may contain multiple Comments.

The relationship can be represented as:

```text
Project
└── Task
    ├── Comment
    ├── Comment
    └── Comment
```
Comments are not shared between Tasks.

A Comment cannot belong to multiple Tasks.

Purpose of Comments

Comments are intended for communication and additional context during the lifecycle of a Task.

For example:
```text
Task:

Title:
Fix date format

Description:
All date fields must use the DD.MM.YYYY format.
```

After the Developer completes the work, they may add:

```text
Comment:

"Date format has been updated and the related fields have been checked."
```

A Project Manager may then add:
```text
"Please also check the date format on the profile page."
```

The Developer may respond:

```text
"The profile page has also been updated."
```

The Task Description defines **what needs to be done**.

Comments describe **communication and context around the work.**

---

## Comment Creation

Users who have access to the Project may create Comments on Tasks.

The following roles may create Comments:

- Organization Owner
- Project Admin
- Project Manager
- Developer

Viewers cannot create Comments in Version 1.

---

## Comment Editing

A user may edit their own Comment.

Users cannot edit Comments created by another user in Version 1.

Comment editing is allowed only within a limited time period after the Comment is created.

The exact editing time window will be defined before implementation.

The Updated At timestamp is updated when a Comment is edited.

Comment editing history is not maintained in Version 1.

Comment version history may be introduced in a future version.

---

### Comment Deletion

Comments use soft deletion rather than immediate physical deletion.

A Comment may be deleted by:

- The Comment author
- Project Admin
- Organization Owner

When a Comment is deleted, it is marked as deleted and excluded from normal active Comment queries.

The underlying Comment data remains available for historical and auditing purposes.

---

## Content Rules

A Comment must contain meaningful text.

The following limits apply in Version 1:

- Minimum length: 1 character
- Maximum length: 2000 characters

Empty or whitespace-only Comments are not allowed.

--- 

## Lifecycle

A Comment does not have an independent workflow lifecycle.

Its availability is determined by the lifecycle of its parent Task.

When a Project is archived, its Tasks and associated Comments become read-only as a consequence of the Project's archived state.

---

## Core Attributes

The Comment contains the following business information:

- Content
- Task
- Author
- Created At
- Updated At
- Deleted At (optional)

Technical implementation details such as database identifiers are intentionally omitted from this document.

---

## Business Rules

The following business rules apply to the Comment domain:

Every Comment must belong to exactly one Task.
A Task may contain multiple Comments.
A Comment cannot belong to multiple Tasks.
Organization Owners may create Comments.
Project Admins may create Comments.
Project Managers may create Comments.
Developers may create Comments.
Viewers cannot create Comments in Version 1.
A user may edit only their own Comment.
Comment editing is allowed only within a limited time period after creation.
The exact Comment editing time window will be defined before implementation.
Project Admins may delete Comments.
Organization Owners may delete Comments.
Comment authors may delete their own Comments.
Comments are soft deleted rather than physically deleted.
Deleted Comments are excluded from normal active Comment queries.
Comments must contain between 1 and 2000 characters.
Comment history is not supported in Version 1.
Comment version history is postponed to a future version.
A Comment does not have an independent workflow state.
Archived Projects make their associated Comments read-only.