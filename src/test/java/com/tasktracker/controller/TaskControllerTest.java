package com.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktracker.dto.CreateTaskRequest;
import com.tasktracker.dto.UpdateTaskRequest;
import com.tasktracker.entity.Task;
import com.tasktracker.entity.TaskStatus;
import com.tasktracker.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldCreateTask() throws Exception {

        Task task = Task.builder()
                .id(1L)
                .title("Buy Milk")
                .status(TaskStatus.TODO)
                .build();

        when(taskService.createTask(any()))
                .thenReturn(task);

        CreateTaskRequest request =
                new CreateTaskRequest();

        request.setTitle("Buy Milk");
        request.setDescription("From Dmart");
        request.setStatus("TODO");
        request.setDueDate(LocalDate.now());
        request.setOwnerId(1L);

        mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetTask() throws Exception {

        Task task = Task.builder()
                .id(1L)
                .title("Buy Milk")
                .status(TaskStatus.TODO)
                .build();

        when(taskService.getTask(1L))
                .thenReturn(task);

        mockMvc.perform(
                        get("/tasks/1")
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllTasks() throws Exception {

        Task task = Task.builder()
                .id(1L)
                .title("Buy Milk")
                .status(TaskStatus.TODO)
                .build();

        when(taskService.getAllTasks(null, null))
                .thenReturn(List.of(task));

        mockMvc.perform(
                        get("/tasks")
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {

        Task task = Task.builder()
                .id(1L)
                .title("Updated Task")
                .status(TaskStatus.DONE)
                .build();

        when(taskService.updateTask(
                any(Long.class),
                any(UpdateTaskRequest.class)
        )).thenReturn(task);

        UpdateTaskRequest request =
                new UpdateTaskRequest();

        request.setTitle("Updated Task");
        request.setDescription("Updated");
        request.setStatus("DONE");
        request.setDueDate(LocalDate.now());

        mockMvc.perform(
                        put("/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteTask() throws Exception {

        doNothing()
                .when(taskService)
                .deleteTask(1L);

        mockMvc.perform(
                        delete("/tasks/1")
                )
                .andExpect(status().isNoContent());
    }
}