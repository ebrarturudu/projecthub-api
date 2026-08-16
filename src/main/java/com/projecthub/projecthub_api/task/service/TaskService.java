package com.projecthub.projecthub_api.task.service;

import com.projecthub.projecthub_api.task.dto.CreateTaskRequest;
import com.projecthub.projecthub_api.task.dto.TaskResponse;

public interface TaskService {

    TaskResponse createTask(Long projectId, CreateTaskRequest request);

}