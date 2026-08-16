package com.projecthub.projecthub_api.projectmember.repository;

import com.projecthub.projecthub_api.projectmember.entity.ProjectMember;
import com.projecthub.projecthub_api.projectmember.entity.ProjectMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectMemberRepository
        extends JpaRepository<ProjectMember, Long> {

    boolean existsByProjectIdAndUserId(
            Long projectId,
            UUID userId
    );

    Optional<ProjectMember> findByProjectIdAndUserId(
            Long projectId,
            UUID userId
    );

    List<ProjectMember> findAllByProjectId(
            Long projectId
    );

    boolean existsByProjectIdAndRole(
            Long projectId,
            ProjectMemberRole role
    );

    void deleteByProjectIdAndUserId(
            Long projectId,
            UUID userId
    );
}