package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.existsById(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User foundUser = userRepository.findById(user.getEmail()).orElse(null);

        if (foundUser == null || !foundUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        return ResponseEntity.ok("Login successful");
    }
}
