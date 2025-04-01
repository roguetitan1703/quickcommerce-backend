
package com.example.quickcommerce.service;

import com.example.quickcommerce.model.User;
import com.example.quickcommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) { // Basic password check, use proper security in real app
            return true;
        }
        return false;
    }
}
