package com.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktracker.dto.CreateUserRequest;
import com.tasktracker.entity.User;
import com.tasktracker.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldCreateUser() throws Exception {

        User savedUser = User.builder()
                .id(1L)
                .name("Asha")
                .email("asha@test.com")
                .build();

        when(userService.createUser(any()))
                .thenReturn(savedUser);

        CreateUserRequest request =
                new CreateUserRequest();

        request.setName("Asha");
        request.setEmail("asha@test.com");

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetUser() throws Exception {

        User user = User.builder()
                .id(1L)
                .name("Asha")
                .email("asha@test.com")
                .build();

        when(userService.getUser(1L))
                .thenReturn(user);

        mockMvc.perform(
                        get("/users/1")
                )
                .andExpect(status().isOk());
    }
}