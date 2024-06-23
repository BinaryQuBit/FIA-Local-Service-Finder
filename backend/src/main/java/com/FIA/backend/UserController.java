package com.FIA.backend;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostServiceRepository postServiceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.existsById(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // Hash the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpSession session) {
        User foundUser = userRepository.findById(user.getEmail()).orElse(null);

        if (foundUser == null || !passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        // Store user email in session
        session.setAttribute("userEmail", user.getEmail());

        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/postservices")
    public ResponseEntity<?> getAllServices() {
        logger.info("Handling GET request for /postservices");
        List<PostService> services = postServiceRepository.findAll();
        logger.info("Fetched services: {}", services);

        if (services.isEmpty()) {
            return ResponseEntity.ok("No services found");
        }

        return ResponseEntity.ok(services);
    }

    @PostMapping("/postservices")
    public ResponseEntity<String> postService(@RequestBody PostService postservice) {
        postServiceRepository.save(postservice);
        return ResponseEntity.ok("Posted Successfully");
    }
}
