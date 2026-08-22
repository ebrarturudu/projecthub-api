package com.projecthub.projecthub_api.task.controller;

import com.projecthub.projecthub_api.task.dto.CreateTaskRequest;
import com.projecthub.projecthub_api.task.dto.TaskResponse;
import com.projecthub.projecthub_api.task.dto.UpdateTaskRequest;
import com.projecthub.projecthub_api.task.service.TaskService;

import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksByProject(
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(
                taskService.getTasksByProject(projectId)
        );
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long projectId,
            @PathVariable Long taskId
    ) {
        return ResponseEntity.ok(
                taskService.getTaskById(projectId, taskId)
        );
    }
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTaskRequest request
    ) {

        return ResponseEntity.ok(
                taskService.updateTask(
                        projectId,
                        taskId,
                        request
                )
        );
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId
    ) {

        taskService.deleteTask(
                projectId,
                taskId
        );

        return ResponseEntity.noContent().build();
    }
}