package com.FIA.backend;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
// import main.java.com.FIA.backend.EmailService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostServiceRepository postServiceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Email  validation
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(user.getEmail());

        if (!matcher.matches()) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }

        // Password validation
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&-])[A-Za-z\\d@$!%*?&-]{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());

        if (!passwordMatcher.matches()) {
            HttpHeaders headers;
            headers = new HttpHeaders();
            headers.add("Content-Type", "text/html; charset=UTF-8");
            return new ResponseEntity<>(
                "Password is invalid. Valid password must contain:<br>" +
                "At least one uppercase letter<br>" +
                "At least one lowercase letter<br>" +
                "At least one digit<br>" +
                "At least one special character: @, $, !, %, *, ?, &, -<br>" +
                "Minimum length of 8 characters",
                headers,
                HttpStatus.BAD_REQUEST
            );
        }

        if (userRepository.existsById(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }

        // Hash the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        if (!userRepository.existsById(email)) {
            return ResponseEntity.status(404).body("User not found");
        }

        userRepository.deleteById(email);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody User user) {
        if (!userRepository.existsById(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email does not exist");
        }

        // Generate reset token
        String token = UUID.randomUUID().toString();
        User existingUser = userRepository.findById(user.getEmail()).orElse(null);
        if (existingUser != null) {
            existingUser.setResetToken(token);
            existingUser.setTokenExpirationTime(LocalDateTime.now().plusMinutes(5));
            userRepository.save(existingUser);

            String resetLink = "http://localhost:3000/ResetPassword?token=" + token + "&email=" + user.getEmail();
            emailService.sendResetPasswordEmail(user.getEmail(), resetLink);

            return ResponseEntity.ok("Check your email for reset password link");
        }

        return ResponseEntity.badRequest().body("Email does not exist");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetUserPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String newPassword = payload.get("newPassword");
        String token = payload.get("token");

        User user = userRepository.findById(email).orElse(null);
        if (user == null || !token.equals(user.getResetToken())) {
            return ResponseEntity.badRequest().body("Invalid email or token");
        }

        if (user.getTokenExpirationTime() == null || LocalDateTime.now().isAfter(user.getTokenExpirationTime())) {
            user.setResetToken(null); // Clear the token
            user.setTokenExpirationTime(null); // Clear the expiration time
            userRepository.save(user);
            return ResponseEntity.badRequest().body("Reset password link has expired");
        }

        // Password validation
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&-])[A-Za-z\\d@$!%*?&-]{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());

        if (!passwordMatcher.matches()) {
            HttpHeaders headers;
            headers = new HttpHeaders();
            headers.add("Content-Type", "text/html; charset=UTF-8");
            return new ResponseEntity<>(
                "Password is invalid. Valid password must contain:<br>" +
                "At least one uppercase letter<br>" +
                "At least one lowercase letter<br>" +
                "At least one digit<br>" +
                "At least one special character: @, $, !, %, *, ?, &, -<br>" +
                "Minimum length of 8 characters",
                headers,
                HttpStatus.BAD_REQUEST
            );
        }

        // Hash the new password before saving
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Clear the token after reset
        user.setTokenExpirationTime(null); // Clear the expiration time after reset
        userRepository.save(user);

        return ResponseEntity.ok("Password has been reset successfully");
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

    @PutMapping("/postservices/{id}")
    public ResponseEntity<String> updatePostStatus(@PathVariable Long id, @RequestBody String status) {
        Optional<PostService> optionalPostService = postServiceRepository.findById(id);
        if (optionalPostService.isEmpty()) {
            return ResponseEntity.status(404).body("Post not found");
        }
    
        PostService postService = optionalPostService.get();
        postService.setStatus(status); // Directly set the status as a string
        postServiceRepository.save(postService);
    
        return ResponseEntity.ok("Status updated successfully");
    }

    @DeleteMapping("/postservices/{id}")
    public ResponseEntity<String> deletePostService(@PathVariable Long id) {
        if (!postServiceRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Post not found");
        }

        postServiceRepository.deleteById(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            return ResponseEntity.status(401).body("User is not logged in");
        }

        User user = userRepository.findById(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok(user);
    }
}
