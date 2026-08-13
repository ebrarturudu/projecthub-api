# 7. Invitation

## Overview

An **Invitation** represents a request to join an Organization or a Project.

An Invitation is created by an authorized user and allows another user to gain access to an Organization or Project after accepting the invitation.

Invitation is a temporary business object that exists between the invitation request and the establishment of the corresponding membership.

In Version 1, invitations are accepted using an invitation code.

---

## Responsibilities

The Invitation domain is responsible for:

* Creating invitations for authorized users.
* Defining the Organization or Project to which access is granted.
* Defining the role assigned through the invitation.
* Tracking the invitation status.
* Allowing pending invitation details to be updated.
* Preventing unauthorized users from creating or modifying invitations.
* Establishing membership after an invitation is accepted.

---

## Invitation Types

Version 1 supports two types of invitations:

* **Organization Invitation**
* **Project Invitation**

### Organization Invitation

An Organization Invitation grants a user access to an Organization.

When the invitation is accepted, an Organization Membership is created.

### Project Invitation

A Project Invitation grants an existing Organization member access to a specific Project.

When the invitation is accepted, a Project Membership is created.

A user must already belong to the Project's Organization before being added to the Project.

---

## Invitation Roles

The role assigned to an invitation depends on its type.

### Organization Invitations

Organization invitations may assign the following roles:

* **ADMIN**
* **MEMBER**

The **OWNER** role cannot be assigned through an invitation.

Ownership is established when an Organization is created and may only be transferred through the defined ownership process.

### Project Invitations

Project invitations may assign the following roles:

* **MANAGER**
* **DEVELOPER**
* **VIEWER**

---

## Invitation Permissions

Invitation creation is restricted according to the user's role.

### Organization Invitations

The following rules apply:

* The Organization Owner may invite Admins and Members.
* The Organization Owner may invite multiple Admins.
* An Organization Admin may invite Members.
* An Organization Admin cannot invite another Admin.
* Neither Admins nor Members can invite an Organization Owner.

### Project Invitations

The following rules apply:

* The Organization Owner may invite Project Managers, Developers, and Viewers.
* The Organization Admin may invite Project Managers, Developers, and Viewers.
* A Project Manager may invite Developers and Viewers.
* A Project Manager cannot invite another Project Manager.
* Developers and Viewers cannot create Project Invitations.

---

## Invitation Lifecycle

An Invitation may exist in one of the following states:

* **PENDING**
* **ACCEPTED**
* **EXPIRED**
* **REVOKED**

### PENDING

The invitation has been created but has not yet been accepted.

While an invitation is pending, its assigned role may be changed by an authorized user.

### ACCEPTED

The invited user has accepted the invitation.

The corresponding Organization Membership or Project Membership is created.

The Invitation record is retained for historical purposes.

### EXPIRED

The invitation is no longer valid because its validity period has ended.

Expired invitations cannot be accepted.

### REVOKED

The invitation has been cancelled by an authorized user before acceptance.

Revoked invitations cannot be accepted.

---

## Invitation Code

Version 1 uses invitation codes as the primary invitation mechanism.

An invitation contains a unique code that the invited user can use to accept the invitation.

Example:

```text
ABC-123
```

The invited user must provide the invitation code through the ProjectHub application.

After successful validation and acceptance:

```text
Invitation
↓
ACCEPTED
↓
Membership Created
```

Email-based invitations, in-app notifications, and automated organization onboarding are postponed to a future version.

----

## Invitation Modification

Pending invitations may be modified by authorized users.

The assigned role may be changed while the invitation is in the PENDING state.

For example:

```text
PENDING
    ↓
DEVELOPER
    ↓
VIEWER
```

The invitation does not need to be cancelled and recreated when only the assigned role changes.

Once an invitation has been accepted, expired, or revoked, its role cannot be modified.

---

## Invitation Acceptance

When an invitation is accepted:

1. The invitation code is validated.
2. The invitation status is checked.
3. The invitation is checked for expiration or revocation.
4. The invited user is identified.
5. The corresponding membership is created.
6. The invitation status becomes **ACCEPTED**.

An invitation cannot be accepted more than once.

---

## Core Attributes

The Invitation contains the following business information:

* Invitation Code
* Invitation Type
* Organization
* Project (optional)
* Invited User
* Invited By
* Assigned Role
* Status
* Created At
* Expires At
* Accepted At (optional)

Technical implementation details such as database identifiers are intentionally omitted from this document.

---

## Business Rules

The following business rules apply to the Invitation domain:

* An Invitation must belong to an Organization.
* A Project Invitation must reference a Project belonging to the same Organization.
* An Organization Invitation creates an Organization Membership when accepted.
* A Project Invitation creates a Project Membership when accepted.
* A Project Invitation can only be accepted by a user who belongs to the Project's Organization.
* Organization Owners can invite Admins and Members.
* Organization Admins can invite Members.
* Organization Admins cannot invite other Admins.
* Project Managers can invite Developers and Viewers.
* Project Managers cannot invite other Project Managers.
* Organization Owners and Admins can invite Project Managers, Developers, and Viewers.
* Owners cannot be assigned through an Invitation.
* Pending Invitations may have their assigned role changed by an authorized user.
* Accepted, Expired, and Revoked Invitations cannot be modified.
* An Invitation can only be accepted once.
* An expired Invitation cannot be accepted.
* A revoked Invitation cannot be accepted.
* Invitation records are retained after acceptance for historical purposes.
* Version 1 uses invitation codes as the invitation mechanism.
* Email-based invitations and advanced onboarding are postponed to a future version.