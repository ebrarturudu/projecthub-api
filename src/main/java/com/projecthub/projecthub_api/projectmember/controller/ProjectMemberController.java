package com.projecthub.projecthub_api.projectmember.controller;

import com.projecthub.projecthub_api.projectmember.dto.AddProjectMemberRequest;
import com.projecthub.projecthub_api.projectmember.dto.ProjectMemberResponse;
import com.projecthub.projecthub_api.projectmember.service.ProjectMemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(
            ProjectMemberService projectMemberService
    ) {
        this.projectMemberService = projectMemberService;
    }

    @PostMapping
    public ResponseEntity<ProjectMemberResponse> addMember(
            @PathVariable Long projectId,
            @Valid @RequestBody AddProjectMemberRequest request
    ) {

        ProjectMemberResponse response =
                projectMemberService.addMember(
                        projectId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectMemberResponse>> getMembers(
            @PathVariable Long projectId
    ) {

        return ResponseEntity.ok(
                projectMemberService.getMembers(projectId)
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long projectId,
            @PathVariable UUID userId
    ) {

        projectMemberService.removeMember(
                projectId,
                userId
        );

        return ResponseEntity.noContent().build();
    }
}