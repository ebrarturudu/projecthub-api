package com.projecthub.projecthub_api.task.controller;

import com.projecthub.projecthub_api.task.dto.CreateTaskRequest;
import com.projecthub.projecthub_api.task.dto.TaskResponse;
import com.projecthub.projecthub_api.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateTaskRequest request
    ) {

        TaskResponse response =
                taskService.createTask(projectId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}