package com.projecthub.projecthub_api.projectmember.service;

import com.projecthub.projecthub_api.project.dto.CreateProjectRequest;
import com.projecthub.projecthub_api.project.entity.Project;
import com.projecthub.projecthub_api.project.repository.ProjectRepository;
import com.projecthub.projecthub_api.projectmember.dto.AddProjectMemberRequest;
import com.projecthub.projecthub_api.projectmember.dto.ProjectMemberResponse;
import com.projecthub.projecthub_api.projectmember.entity.ProjectMember;
import com.projecthub.projecthub_api.projectmember.entity.ProjectMemberRole;
import com.projecthub.projecthub_api.projectmember.repository.ProjectMemberRepository;
import com.projecthub.projecthub_api.User.entity.User;
import com.projecthub.projecthub_api.User.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ProjectMemberResponse addMember(
            Long projectId,
            AddProjectMemberRequest request
    ) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Project not found")
                );

        ProjectMember currentMember =
                getCurrentUserProjectMembership(projectId);

        if (currentMember.getRole() != ProjectMemberRole.PROJECT_MANAGER) {
            throw new AccessDeniedException(
                    "Only project manager can add members"
            );
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        if (projectMemberRepository.existsByProjectIdAndUserId(
                projectId,
                request.getUserId()
        )) {
            throw new IllegalStateException(
                    "User is already a member of this project"
            );
        }

        ProjectMember projectMember = new ProjectMember();

        projectMember.setProject(project);
        projectMember.setUser(user);
        projectMember.setRole(request.getRole());

        ProjectMember savedMember =
                projectMemberRepository.save(projectMember);

        return toResponse(savedMember);
    }

    public List<ProjectMemberResponse> getMembers(Long projectId) {

        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project not found");
        }

        getCurrentUserProjectMembership(projectId);

        return projectMemberRepository.findAllByProjectId(projectId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void removeMember(
            Long projectId,
            UUID userId
    ) {

        ProjectMember currentMember =
                getCurrentUserProjectMembership(projectId);

        if (currentMember.getRole() != ProjectMemberRole.PROJECT_MANAGER) {
            throw new AccessDeniedException(
                    "Only project manager can remove members"
            );
        }

        ProjectMember memberToRemove =
                projectMemberRepository.findByProjectIdAndUserId(
                        projectId,
                        userId
                ).orElseThrow(() ->
                        new IllegalArgumentException(
                                "User is not a member of this project"
                        )
                );

        if (memberToRemove.getRole()
                == ProjectMemberRole.PROJECT_MANAGER) {

            throw new IllegalStateException(
                    "Project manager cannot be removed"
            );
        }

        if (currentMember.getUser().getId().equals(userId)) {

            throw new IllegalStateException(
                    "You cannot remove yourself from the project"
            );
        }

        projectMemberRepository.delete(memberToRemove);
    }

    private ProjectMember getCurrentUserProjectMembership(
            Long projectId
    ) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Current user not found"
                        )
                );

        return projectMemberRepository
                .findByProjectIdAndUserId(
                        projectId,
                        currentUser.getId()
                )
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "You are not a member of this project"
                        )
                );
    }

    private ProjectMemberResponse toResponse(
            ProjectMember member
    ) {

        return new ProjectMemberResponse(
                member.getId(),
                member.getProject().getId(),
                member.getUser().getId(),
                member.getRole()
        );
    }
}

