package com.projecthub.projecthub_api.projectmember.dto;

import com.projecthub.projecthub_api.projectmember.entity.ProjectMemberRole;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AddProjectMemberRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private ProjectMemberRole role;

    public AddProjectMemberRequest() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public ProjectMemberRole getRole() {
        return role;
    }

    public void setRole(ProjectMemberRole role) {
        this.role = role;
    }
}
