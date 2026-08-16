package com.projecthub.projecthub_api.project.service;

import com.projecthub.projecthub_api.project.dto.CreateProjectRequest;
import com.projecthub.projecthub_api.project.entity.Project;
import com.projecthub.projecthub_api.project.repository.ProjectRepository;
import com.projecthub.projecthub_api.projectmember.entity.ProjectMember;
import com.projecthub.projecthub_api.projectmember.entity.ProjectMemberRole;
import com.projecthub.projecthub_api.projectmember.repository.ProjectMemberRepository;
import com.projecthub.projecthub_api.User.entity.User;
import com.projecthub.projecthub_api.User.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            ProjectMemberRepository projectMemberRepository,
            UserRepository userRepository
    ) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.userRepository = userRepository;
    }

    public Project createProject(CreateProjectRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        Project project = new Project();

        project.setName(request.getName());
        project.setDescription(request.getDescription());

        Project savedProject = projectRepository.save(project);

        ProjectMember projectManager = new ProjectMember();

        projectManager.setProject(savedProject);
        projectManager.setUser(user);
        projectManager.setRole(ProjectMemberRole.PROJECT_MANAGER);

        projectMemberRepository.save(projectManager);

        return savedProject;
    }
}