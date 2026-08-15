package com.projecthub.projecthub_api.project.dto;

import com.projecthub.projecthub_api.User.entity.Role;

import java.util.UUID;

public class ProjectMemberResponse {

    private Long id;
    private Long projectId;
    private UUID userId;
    private Role role;

    public ProjectMemberResponse() {
    }

    public ProjectMemberResponse(
            Long id,
            Long projectId,
            UUID userId,
            Role role
    ) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public UUID getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }
}