package com.tasktracker.service;

import com.tasktracker.dto.CreateTaskRequest;
import com.tasktracker.dto.UpdateTaskRequest;
import com.tasktracker.entity.Task;
import com.tasktracker.entity.TaskStatus;
import com.tasktracker.entity.User;
import com.tasktracker.exception.TaskNotFoundException;
import com.tasktracker.repository.TaskRepository;
import com.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private static final Logger log =
            LoggerFactory.getLogger(TaskService.class);

    public Task createTask(CreateTaskRequest request) {

        log.info(
                "Creating task {}",
                request.getTitle()
        );

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.valueOf(request.getStatus()))
                .dueDate(request.getDueDate())
                .owner(owner)
                .build();

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(
            String status,
            Long ownerId) {

        if (status != null && ownerId != null) {

            return taskRepository
                    .findByStatusAndOwnerId(
                            TaskStatus.valueOf(status),
                            ownerId
                    );
        }

        if (status != null) {

            return taskRepository.findByStatus(
                    TaskStatus.valueOf(status)
            );
        }

        if (ownerId != null) {

            return taskRepository.findByOwnerId(
                    ownerId
            );
        }

        return taskRepository.findAll();
    }

    public Task getTask(Long id) {

        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(id));
    }

    public void deleteTask(Long id) {

        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }

        taskRepository.deleteById(id);
    }

    public Task updateTask(
            Long id,
            UpdateTaskRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(id));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        task.setStatus(
                TaskStatus.valueOf(
                        request.getStatus()));

        task.setDueDate(request.getDueDate());

        return taskRepository.save(task);
    }
}

