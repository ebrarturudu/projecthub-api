package com.projecthub.projecthub_api.task.service;

import com.projecthub.projecthub_api.project.entity.Project;
import com.projecthub.projecthub_api.project.repository.ProjectRepository;
import com.projecthub.projecthub_api.task.dto.CreateTaskRequest;
import com.projecthub.projecthub_api.task.dto.TaskResponse;
import com.projecthub.projecthub_api.task.entity.Task;
import com.projecthub.projecthub_api.task.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            ProjectRepository projectRepository
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public TaskResponse createTask(Long projectId, CreateTaskRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Project not found")
                );

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setProject(project);

        Task savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    private TaskResponse mapToResponse(Task task) {

        TaskResponse response = new TaskResponse();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setProjectId(task.getProject().getId());
        response.setDueDate(task.getDueDate());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        return response;
    }
}