package com.tasktracker.controller;

import com.tasktracker.dto.CreateUserRequest;
import com.tasktracker.entity.User;
import com.tasktracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(
            @Valid
            @RequestBody CreateUserRequest request) {

        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public User getUser(
            @Valid
            @PathVariable Long id) {

        return userService.getUser(id);
    }
}