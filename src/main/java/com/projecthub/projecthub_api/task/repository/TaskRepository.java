package com.projecthub.projecthub_api.task.repository;

import com.projecthub.projecthub_api.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByProjectId(Long projectId);

    Optional<Task> findByIdAndProjectId(Long taskId, Long projectId);
}