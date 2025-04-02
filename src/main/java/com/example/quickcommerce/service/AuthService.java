package com.example.quickcommerce.service;

import com.example.quickcommerce.model.User;
import com.example.quickcommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Authenticate a user with username and password
     * In a real app, this would use proper password hashing
     */
    public User authenticate(String username, String password) {
        logger.info("Authentication attempt for username: {}", username);

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            logger.warn("Authentication failed: User not found with username: {}", username);
            throw new IllegalArgumentException("Invalid username or password");
        }

        User user = userOpt.get();
        logger.debug("User found: {}, userId: {}", user.getUsername(), user.getUserId());

        // Very basic password check (in a real app, use proper password hashing!)
        if (!user.getPassword().equals(password)) {
            logger.warn("Authentication failed: Invalid password for username: {}", username);
            throw new IllegalArgumentException("Invalid username or password");
        }

        logger.info("Authentication successful for user: {}", username);
        return user;
    }

    /**
     * Register a new user
     * In a real app, this would include password hashing
     */
    public User register(User user) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // In a real app, hash the password before saving
        // user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
