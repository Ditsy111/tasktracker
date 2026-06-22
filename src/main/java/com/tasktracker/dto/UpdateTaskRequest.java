package com.tasktracker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTaskRequest {

    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
}