package com.projecthub.projecthub_api.projectmember.dto;

import com.projecthub.projecthub_api.projectmember.entity.ProjectMemberRole;

import java.util.UUID;

public class ProjectMemberResponse {

    private Long id;
    private Long projectId;
    private UUID userId;
    private ProjectMemberRole role;

    public ProjectMemberResponse() {
    }

    public ProjectMemberResponse(
            Long id,
            Long projectId,
            UUID userId,
            ProjectMemberRole role
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

    public ProjectMemberRole getRole() {
        return role;
    }
}