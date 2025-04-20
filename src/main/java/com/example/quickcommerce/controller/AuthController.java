package com.example.quickcommerce.controller;

import com.example.quickcommerce.dto.ApiResponse;
import com.example.quickcommerce.dto.LoginRequest;
import com.example.quickcommerce.dto.LoginResponse;
import com.example.quickcommerce.model.User;
import com.example.quickcommerce.service.AuthService;
import com.example.quickcommerce.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    // POST /api/auth/login - User login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt received for email: {}", loginRequest.getEmail());

        if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
            logger.warn("Login failed: Email is empty");
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email is required"));
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            logger.warn("Login failed: Password is empty");
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Password is required"));
        }

        try {
            User user = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            String token = jwtService.generateToken(user);

            LoginResponse response = new LoginResponse(token, user);
            logger.info("Login successful for user: {}", user.getEmail());
            return ResponseEntity.ok(new ApiResponse(true, "Login successful", response));
        } catch (IllegalArgumentException e) {
            logger.warn("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            logger.error("Login error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An error occurred during login"));
        }
    }

    // POST /api/auth/register - User registration
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        logger.info("User registration attempt: {}", user.getEmail());

        if (user.getEmail() == null || user.getPassword() == null) {
            logger.warn("Registration failed: Missing email or password");
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email and password are required"));
        }

        try {
            User registeredUser = authService.register(user);
            logger.info("User registered successfully: {}", user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "User registered successfully", registeredUser));
        } catch (IllegalArgumentException e) {
            logger.warn("Registration failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            logger.error("Registration error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An error occurred during registration"));
        }
    }
}
