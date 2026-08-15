package com.projecthub.projecthub_api.project.service;

import com.projecthub.projecthub_api.User.entity.Role;
import com.projecthub.projecthub_api.User.entity.User;
import com.projecthub.projecthub_api.User.repository.UserRepository;
import com.projecthub.projecthub_api.project.entity.Project;
import com.projecthub.projecthub_api.project.entity.ProjectMember;
import com.projecthub.projecthub_api.project.repository.ProjectMemberRepository;
import com.projecthub.projecthub_api.project.repository.ProjectRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                projectMemberRepository.findByProjectIdAndUserId(
                        projectId,
                        currentUser.getId()
                ).orElseThrow(() ->
                        new AccessDeniedException(
                                "You are not a member of this project"
                        )
                );

        Role currentUserRole = currentMember.getRole();

        if (currentUserRole != Role.OWNER
                && currentUserRole != Role.ADMIN
                && currentUserRole != Role.PROJECT_MANAGER) {

            throw new AccessDeniedException(
                    "You do not have permission to add members"
            );
        }

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