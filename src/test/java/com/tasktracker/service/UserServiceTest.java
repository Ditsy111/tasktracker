package com.tasktracker.service;

import com.tasktracker.dto.CreateUserRequest;
import com.tasktracker.entity.Task;
import com.tasktracker.entity.TaskStatus;
import com.tasktracker.entity.User;
import com.tasktracker.exception.TaskNotFoundException;
import com.tasktracker.exception.UserNotFoundException;
import com.tasktracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
        CreateUserRequest request =
                new CreateUserRequest();

        request.setName("Asha");
        request.setEmail("asha@test.com");

        User savedUser = User.builder()
                .id(1L)
                .name("Asha")
                .email("asha@test.com")
                .build();

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        User createUser = userService.createUser(request);

        assertEquals(1L, createUser.getId());

        assertEquals(
                "Asha",
                createUser.getName()
        );

        assertEquals(
                "asha@test.com",
                createUser.getEmail()
        );

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void shouldGetUser() {

        User user = User.builder()
                .id(1L)
                .name("Asha")
                .email("asha@test.com")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result =
                userService.getUser(1L);

        assertEquals(
                "Asha",
                result.getName()
        );

        assertEquals(
                "asha@test.com",
                result.getEmail()
        );
    }


    @Test
    void shouldThrowUserNotFoundException() {

        when(userRepository.findById(100L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.getUser(100L)
        );
    }

}