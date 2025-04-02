package com.example.quickcommerce.controller;

import com.example.quickcommerce.model.User;
import com.example.quickcommerce.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    // POST /api/auth/login - User login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        logger.info("Login attempt received for username: {}", username);

        if (username == null || password == null) {
            logger.warn("Login failed: Missing username or password");
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        try {
            User authenticatedUser = authService.authenticate(username, password);
            logger.info("User authenticated successfully: {}", username);

            // In a real app, generate JWT token here
            String token = "sample-jwt-token-" + System.currentTimeMillis();

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", authenticatedUser);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during login: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }

    // POST /api/auth/register - User registration
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        logger.info("User registration attempt: {}", user.getUsername());

        if (user.getUsername() == null || user.getPassword() == null) {
            logger.warn("Registration failed: Missing username or password");
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        try {
            User registeredUser = authService.register(user);
            logger.info("User registered successfully: {}", user.getUsername());
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.warn("Registration failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during registration: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }
}
