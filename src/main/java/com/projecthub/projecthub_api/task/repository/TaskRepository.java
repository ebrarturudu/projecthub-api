package com.projecthub.projecthub_api.task.repository;

import com.projecthub.projecthub_api.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}