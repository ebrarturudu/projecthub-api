package com.projecthub.projecthub_api.task.service;

import com.projecthub.projecthub_api.User.entity.User;
import com.projecthub.projecthub_api.User.repository.UserRepository;
import com.projecthub.projecthub_api.project.entity.Project;
import com.projecthub.projecthub_api.project.repository.ProjectRepository;
import com.projecthub.projecthub_api.projectmember.entity.ProjectMember;
import com.projecthub.projecthub_api.projectmember.entity.ProjectMemberRole;
import com.projecthub.projecthub_api.projectmember.repository.ProjectMemberRepository;
import com.projecthub.projecthub_api.task.dto.CreateTaskRequest;
import com.projecthub.projecthub_api.task.dto.TaskResponse;
import com.projecthub.projecthub_api.task.entity.Task;
import com.projecthub.projecthub_api.task.repository.TaskRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            ProjectMemberRepository projectMemberRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskResponse createTask(
            Long projectId,
            CreateTaskRequest request
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Current user not found")
                );

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Project not found")
                );

        ProjectMember currentMember =
                projectMemberRepository
                        .findByProjectIdAndUserId(
                                projectId,
                                currentUser.getId()
                        )
                        .orElseThrow(() ->
                                new AccessDeniedException(
                                        "You are not a member of this project"
                                )
                        );

        if (currentMember.getRole()
                != ProjectMemberRole.PROJECT_MANAGER) {

            throw new AccessDeniedException(
                    "Only project managers can create tasks"
            );
        }

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setProject(project);

        if (request.getAssigneeId() != null) {

            UUID assigneeId = request.getAssigneeId();

            User assignee = userRepository.findById(assigneeId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Assignee not found"
                            )
                    );

            boolean isProjectMember =
                    projectMemberRepository
                            .existsByProjectIdAndUserId(
                                    projectId,
                                    assigneeId
                            );

            if (!isProjectMember) {
                throw new IllegalArgumentException(
                        "Assignee must be a member of the project"
                );
            }

            task.setAssignee(assignee);
        }

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

        if (task.getAssignee() != null) {
            response.setAssigneeId(
                    task.getAssignee().getId()
            );
        }

        response.setDueDate(task.getDueDate());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        return response;
    }
}

