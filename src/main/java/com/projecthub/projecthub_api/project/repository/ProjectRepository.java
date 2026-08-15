package com.projecthub.projecthub_api.project.repository;

import com.projecthub.projecthub_api.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}