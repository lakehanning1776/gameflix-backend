package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void register_ShouldCreateUser() {
        AuthRequest request = new AuthRequest();
        request.setUsername("lake");
        request.setPassword("password123");

        when(userRepository.existsByUsername("lake")).thenReturn(false);

        Map<String, String> result = userService.register(request);

        assertEquals("User registered successfully", result.get("message"));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_ShouldRejectDuplicateUsername() {
        AuthRequest request = new AuthRequest();
        request.setUsername("lake");
        request.setPassword("password123");

        when(userRepository.existsByUsername("lake")).thenReturn(true);

        Map<String, String> result = userService.register(request);

        assertEquals("Username already exists", result.get("message"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_ShouldReturnSuccessForValidCredentials() {
        AuthRequest request = new AuthRequest();
        request.setUsername("lake");
        request.setPassword("password123");

        String encodedPassword = passwordEncoder.encode("password123");
        User user = new User("lake", encodedPassword);

        when(userRepository.findByUsername("lake"))
                .thenReturn(Optional.of(user));

        Map<String, String> result = userService.login(request);

        assertEquals("Login successful", result.get("message"));
    }
}