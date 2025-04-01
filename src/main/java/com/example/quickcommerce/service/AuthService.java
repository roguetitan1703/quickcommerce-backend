package com.example.quickcommerce.service;

import com.example.quickcommerce.model.User;
import com.example.quickcommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Authenticate a user with username and password
     * In a real app, this would use proper password hashing
     */
    public User authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        User user = userOpt.get();

        // Very basic password check (in a real app, use proper password hashing!)
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }

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
