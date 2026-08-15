package com.projecthub.projecthub_api.project.repository;

import com.projecthub.projecthub_api.project.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    boolean existsByProjectIdAndUserId(Long projectId, UUID userId);

    Optional<ProjectMember> findByProjectIdAndUserId(
            Long projectId,
            UUID userId
    );
}