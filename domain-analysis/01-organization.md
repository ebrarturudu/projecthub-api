# Domain Analysis

## Purpose

This document describes the core business domains of ProjectHub and defines the responsibilities, boundaries, and lifecycle of each domain.

The goal of this document is to establish a shared understanding of the business model before implementation begins. Every domain object should have a single responsibility and represent a real business concept rather than a database table.

This document will evolve throughout the project as new domains are introduced.

---

# 1. Organization

## Overview

The **Organization** is the highest-level business entity in ProjectHub.

It represents a company, team, or business unit that owns projects and manages its members. Every other domain object in the system belongs to an Organization either directly or indirectly.

An Organization provides isolation between different companies using the platform. Members of one Organization cannot access resources that belong to another Organization.

An Organization acts as the top-level boundary for all business operations within the system.

---

## Responsibilities

The Organization domain is responsible for:

* Managing organization information.
* Owning projects.
* Managing organization members.
* Defining the highest level of access boundaries.
* Providing a workspace for collaboration.

---

## Membership

A user can belong to **only one Organization** in Version 1.

Users cannot join an Organization directly.

Membership is established only through an invitation sent by an authorized user.

During the invitation process, the member's initial role is assigned.

Only existing Organization members can invite new members, subject to their permissions.

---

## Ownership

The user who creates an Organization automatically becomes its **Owner**.

The Owner is the highest authority within the Organization.

Ownership cannot be abandoned.

Before leaving an Organization, the current Owner must transfer ownership to another member.

---

## Lifecycle

An Organization may exist in one of the following states:

* **ACTIVE**
* **ARCHIVED**

Archived Organizations remain accessible for historical purposes but become read-only and cannot perform new write operations.

Deletion is intentionally excluded from Version 1 and may be introduced in a future release with an appropriate data retention policy.

---

## Core Attributes

The Organization contains the following business information:

* Name
* Description
* Logo (optional)
* Status
* Created At
* Updated At
* Archived At (optional)

Technical implementation details such as database identifiers are intentionally omitted from this document.

---

## Business Rules

The following business rules apply to the Organization domain:

* Every Organization must have exactly one Owner.
* Every Organization can contain multiple members.
* Every Organization can own multiple Projects.
* A user may belong to only one Organization in Version 1.
* Users may join an Organization only through an invitation.
* The Organization creator automatically becomes the Owner.
* Ownership must be transferred before the current Owner leaves the Organization.
* Archived Organizations cannot create new Projects.

---

## Future Improvements (V2+) silinecek

The following features are intentionally postponed:

* Multiple Organization membership
* Multiple Organization ownership
* Organization URL (Slug)
* Organization Settings
* Organization Localization
* Configurable data retention policy
* Advanced branding and customization
