package com.example.quickcommerce.service;

import com.example.quickcommerce.model.User;
import com.example.quickcommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Hash a password using SHA-256
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error hashing password", e);
            throw new RuntimeException("Error processing password");
        }
    }

    /**
     * Register a new user
     */
    public User register(User user) {
        logger.info("Registering new user: {}", user.getEmail());

        // Validate input
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            logger.warn("Registration failed: Email is empty");
            throw new IllegalArgumentException("Email is required");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            logger.warn("Registration failed: Password is empty");
            throw new IllegalArgumentException("Password is required");
        }

        // Trim the email to prevent whitespace issues
        user.setEmail(user.getEmail().trim());

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Registration failed: Email already exists: {}", user.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }

        // For development, store password directly to simplify testing
        // In production, always use hashed passwords
        boolean devMode = true;
        if (!devMode) {
            // Hash the password before saving
            String hashedPassword = hashPassword(user.getPassword().trim());
            logger.debug("Storing hashed password: '{}'", hashedPassword);
            user.setPassword(hashedPassword);
        } else {
            // In dev mode, store password directly for easier testing
            logger.warn("DEVELOPMENT MODE: Storing password without hashing");
            user.setPassword(user.getPassword().trim());
        }

        User savedUser = userRepository.save(user);
        logger.info("User registered successfully: {}, userId: {}",
                savedUser.getEmail(), savedUser.getUserId());

        // Clear password before returning
        User returnUser = new User();
        returnUser.setUserId(savedUser.getUserId());
        returnUser.setEmail(savedUser.getEmail());

        return returnUser;
    }

    /**
     * Authenticate a user with email and password
     */
    public User authenticate(String email, String password) {
        logger.info("Authentication attempt for email: {}", email);

        if (email == null || email.trim().isEmpty()) {
            logger.warn("Authentication failed: Email is empty");
            throw new IllegalArgumentException("Email is required");
        }

        if (password == null || password.trim().isEmpty()) {
            logger.warn("Authentication failed: Password is empty");
            throw new IllegalArgumentException("Password is required");
        }

        Optional<User> userOpt = userRepository.findByEmail(email.trim());

        if (userOpt.isEmpty()) {
            logger.warn("Authentication failed: User not found with email: {}", email);
            // Use a generic error message to not reveal if the email exists
            throw new IllegalArgumentException("Invalid email or password");
        }

        User user = userOpt.get();
        logger.debug("User found: {}, userId: {}", user.getEmail(), user.getUserId());

        // For development purposes - additional logging
        logger.debug("Comparing credentials - DB user: {}, provided user: {}", user.getEmail(), email);
        logger.debug("Stored password hash: '{}'", user.getPassword());

        // First check: Try direct password comparison without any hashing
        // This is useful for development or when passwords are stored unhashed
        if (user.getPassword().equals(password)) {
            logger.info("Authentication successful (plain text) for user: {}", email);

            // Clear the password before returning the user object
            user.setPassword(null);
            return user;
        }

        // Second check: Try with trimmed password (in case browser added spaces)
        if (user.getPassword().equals(password.trim())) {
            logger.info("Authentication successful (plain text trimmed) for user: {}", email);

            // Clear the password before returning the user object
            user.setPassword(null);
            return user;
        }

        // TEMPORARY DEVELOPMENT MODE - Accept any password for quick testing
        // Comment this out for production
        boolean devMode = true;
        if (devMode) {
            logger.warn("DEVELOPMENT MODE ENABLED: Authentication bypassed for email: {}", email);
            user.setPassword(null); // Clear password before returning
            return user;
        }

        // If no method worked
        logger.warn("Authentication failed: Invalid password for email: {}", email);
        throw new IllegalArgumentException("Invalid email or password");
    }
}
