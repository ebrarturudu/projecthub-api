package com.projecthub.projecthub_api.project.controller;

import com.projecthub.projecthub_api.project.dto.AddProjectMemberRequest;
import com.projecthub.projecthub_api.project.dto.ProjectMemberResponse;
import com.projecthub.projecthub_api.project.entity.ProjectMember;
import com.projecthub.projecthub_api.project.service.ProjectMemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @PostMapping("/{projectId}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectMemberResponse addMember(
            @PathVariable Long projectId,
            @Valid @RequestBody AddProjectMemberRequest request
    ) {

        ProjectMember projectMember = projectMemberService.addMember(
                projectId,
                request.getUserId(),
                request.getRole()
        );

        return new ProjectMemberResponse(
                projectMember.getId(),
                projectMember.getProject().getId(),
                projectMember.getUser().getId(),
                projectMember.getRole()
        );
    }
}