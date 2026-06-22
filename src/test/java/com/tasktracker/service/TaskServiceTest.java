package com.tasktracker.service;

import com.tasktracker.dto.CreateTaskRequest;
import com.tasktracker.dto.UpdateTaskRequest;
import com.tasktracker.entity.*;
import com.tasktracker.exception.TaskNotFoundException;
import com.tasktracker.repository.TaskRepository;
import com.tasktracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldCreateTask() {

        User owner = User.builder()
                .id(1L)
                .name("Asha")
                .email("asha@test.com")
                .build();

        CreateTaskRequest request =
                new CreateTaskRequest();

        request.setTitle("Buy Milk");
        request.setDescription("2 litres");
        request.setStatus("TODO");
        request.setDueDate(LocalDate.now());
        request.setOwnerId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(owner));

        Task savedTask = Task.builder()
                .id(1L)
                .title("Buy Milk")
                .description("2 litres")
                .status(TaskStatus.TODO)
                .owner(owner)
                .build();

        when(taskRepository.save(any(Task.class)))
                .thenReturn(savedTask);

        Task result =
                taskService.createTask(request);

        assertEquals(1L, result.getId());

        assertEquals(
                "Buy Milk",
                result.getTitle()
        );

        assertEquals(
                TaskStatus.TODO,
                result.getStatus()
        );

        verify(taskRepository, times(1))
                .save(any(Task.class));
    }

    @Test
    void shouldGetTask() {

        Task task = Task.builder()
                .id(1L)
                .title("Buy Milk")
                .status(TaskStatus.TODO)
                .build();

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        Task result =
                taskService.getTask(1L);

        assertEquals(
                "Buy Milk",
                result.getTitle()
        );
    }

    @Test
    void shouldThrowTaskNotFoundException() {

        when(taskRepository.findById(100L))
                .thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTask(100L)
        );
    }

    @Test
    void shouldThrowExceptionWhenDeletingMissingTask() {

        when(taskRepository.existsById(100L))
                .thenReturn(false);

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.deleteTask(100L)
        );
    }

    @Test
    void shouldThrowWhenOwnerNotFound() {

        CreateTaskRequest request = new CreateTaskRequest();
        request.setOwnerId(99L);

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> taskService.createTask(request)
        );
    }

    @Test
    void shouldGetTasksByStatusAndOwner() {

        List<Task> tasks = List.of(new Task());

        when(taskRepository.findByStatusAndOwnerId(
                TaskStatus.TODO,
                1L))
                .thenReturn(tasks);

        List<Task> result =
                taskService.getAllTasks(
                        "TODO",
                        1L
                );

        assertEquals(1, result.size());
    }

    @Test
    void shouldGetTasksByStatus() {

        List<Task> tasks = List.of(new Task());

        when(taskRepository.findByStatus(
                TaskStatus.TODO))
                .thenReturn(tasks);

        List<Task> result =
                taskService.getAllTasks(
                        "TODO",
                        null
                );

        assertEquals(1, result.size());
    }

    @Test
    void shouldGetTasksByOwner() {

        List<Task> tasks = List.of(new Task());

        when(taskRepository.findByOwnerId(1L))
                .thenReturn(tasks);

        List<Task> result =
                taskService.getAllTasks(
                        null,
                        1L
                );

        assertEquals(1, result.size());
    }

    @Test
    void shouldGetAllTasksWithoutFilters() {

        List<Task> tasks = List.of(new Task());

        when(taskRepository.findAll())
                .thenReturn(tasks);

        List<Task> result =
                taskService.getAllTasks(
                        null,
                        null
                );

        assertEquals(1, result.size());
    }

    @Test
    void shouldDeleteTask() {

        when(taskRepository.existsById(1L))
                .thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository)
                .deleteById(1L);
    }

    @Test
    void shouldUpdateTask() {

        Task task = Task.builder()
                .id(1L)
                .title("Old")
                .status(TaskStatus.TODO)
                .build();

        UpdateTaskRequest request =
                new UpdateTaskRequest();

        request.setTitle("New");
        request.setDescription("Updated");
        request.setStatus("DONE");
        request.setDueDate(LocalDate.now());

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(any(Task.class)))
                .thenReturn(task);

        Task result =
                taskService.updateTask(
                        1L,
                        request
                );

        assertEquals(
                "New",
                result.getTitle()
        );
    }
}


