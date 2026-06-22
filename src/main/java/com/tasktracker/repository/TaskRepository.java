package com.tasktracker.repository;

import com.tasktracker.entity.Task;
import com.tasktracker.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByOwnerId(Long ownerId);

    List<Task> findByStatusAndOwnerId(
            TaskStatus status,
            Long ownerId
    );
}