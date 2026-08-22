package com.projecthub.projecthub_api.task.service;

import com.projecthub.projecthub_api.task.dto.CreateTaskRequest;
import com.projecthub.projecthub_api.task.dto.TaskResponse;
import com.projecthub.projecthub_api.task.dto.UpdateTaskRequest;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(
            Long projectId,
            CreateTaskRequest request
    );

    List<TaskResponse> getTasksByProject(
            Long projectId
    );

    TaskResponse getTaskById(
            Long projectId,
            Long taskId
    );

    TaskResponse updateTask(
            Long projectId,
            Long taskId,
            UpdateTaskRequest request
    );

    void deleteTask(
            Long projectId,
            Long taskId
    );
}