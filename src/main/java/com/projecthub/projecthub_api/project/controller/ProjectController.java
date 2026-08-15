package com.projecthub.projecthub_api.project.controller;

import com.projecthub.projecthub_api.project.dto.CreateProjectRequest;
import com.projecthub.projecthub_api.project.entity.Project;
import com.projecthub.projecthub_api.project.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(
            @Valid @RequestBody CreateProjectRequest request
    ) {
        return projectService.createProject(request);
    }
}