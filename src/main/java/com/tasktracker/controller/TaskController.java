package com.tasktracker.controller;

import com.tasktracker.dto.CreateTaskRequest;
import com.tasktracker.dto.UpdateTaskRequest;
import com.tasktracker.entity.Task;
import com.tasktracker.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(
            @RequestBody CreateTaskRequest request) {

        return taskService.createTask(request);
    }

    @GetMapping
    public List<Task> getAllTasks(

            @RequestParam(required = false)
            String status,

            @RequestParam(required = false)
            Long ownerId) {

        return taskService.getAllTasks(
                status,
                ownerId
        );
    }

    @GetMapping("/{id}")
    public Task getTask(
            @PathVariable Long id) {

        return taskService.getTask(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(
            @Valid
            @PathVariable Long id) {

        taskService.deleteTask(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(
            @Valid
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest request) {

        return taskService.updateTask(id, request);
    }
}