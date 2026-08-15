package com.projecthub.projecthub_api.project.repository;

import com.projecthub.projecthub_api.project.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
}