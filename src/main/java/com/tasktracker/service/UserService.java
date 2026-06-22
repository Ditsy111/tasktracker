package com.tasktracker.service;

import com.tasktracker.dto.CreateUserRequest;
import com.tasktracker.entity.User;
import com.tasktracker.exception.UserNotFoundException;
import com.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public User createUser(CreateUserRequest request) {

        log.info(
                "Creating user with email {}",
                request.getEmail()
        );

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        return userRepository.save(user);
    }

    public User getUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id));
    }
}