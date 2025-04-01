
package com.example.quickcommerce.controller;

import com.example.quickcommerce.model.User;
import com.example.quickcommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (authService.authenticateUser(user.getUsername(), user.getPassword())) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
}
