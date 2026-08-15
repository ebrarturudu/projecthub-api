package com.projecthub.projecthub_api.project.service;

import com.projecthub.projecthub_api.User.entity.Role;
import com.projecthub.projecthub_api.User.entity.User;
import com.projecthub.projecthub_api.User.repository.UserRepository;
import com.projecthub.projecthub_api.project.entity.Project;
import com.projecthub.projecthub_api.project.entity.ProjectMember;
import com.projecthub.projecthub_api.project.repository.ProjectMemberRepository;
import com.projecthub.projecthub_api.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectMemberService(
            ProjectMemberRepository projectMemberRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectMember addMember(
            Long projectId,
            UUID userId,
            Role role
    ) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Project not found")
                );

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        boolean alreadyMember =
                projectMemberRepository.existsByProjectIdAndUserId(
                        projectId,
                        userId
                );

        if (alreadyMember) {
            throw new IllegalStateException(
                    "User is already a member of this project"
            );
        }

        ProjectMember projectMember = new ProjectMember();

        projectMember.setProject(project);
        projectMember.setUser(user);
        projectMember.setRole(role);

        return projectMemberRepository.save(projectMember);
    }
}