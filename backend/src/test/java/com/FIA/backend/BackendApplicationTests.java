package com.FIA.backend;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Mock
        private UserRepository userRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Mock
        private PostServiceRepository postServiceRepository;

        @InjectMocks
        private UserController userController;

        private User user;

        @BeforeEach
        public void setUp() {
                MockitoAnnotations.openMocks(this);
                user = new User();
                user.setEmail("test@example.com");
                user.setPassword("password123");
        }

        @Test
        public void testValidUser() throws Exception {
                User validUser = new User();
                validUser.setEmail("valid.email91@example.com");
                validUser.setPassword("validPassword-123");

                when(userRepository.existsById(validUser.getEmail())).thenReturn(false);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User registered successfully"));

                // Delete the user after test
                mockMvc.perform(delete("/api/users/delete/" + validUser.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        }

        @Test
        public void testInvalidEmail() throws Exception {
                User invalidUser = new User();
                invalidUser.setEmail("invalid-email");
                invalidUser.setPassword("validPassword123");

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string("Invalid email format"));
        }

        @Test
        public void testInvalidPassword() throws Exception {
                User invalidPasswordUser = new User();
                invalidPasswordUser.setEmail("valid99@example.com");
                invalidPasswordUser.setPassword("short");

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPasswordUser)))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(
                                "Password is invalid. Valid password must contain:<br>" +
                                "At least one uppercase letter<br>" +
                                "At least one lowercase letter<br>" +
                                "At least one digit<br>" +
                                "At least one special character: @, $, !, %, *, ?, & or -<br>" +
                                "Minimum length of 8 characters<br>" +
                                "No spaces"));
        }
        
        @Test
        public void testValidPassword() throws Exception {
                User validPasswordUser = new User();
                validPasswordUser.setEmail("valid101@example.com");
                validPasswordUser.setPassword("validPassword-123");

                when(userRepository.existsById(validPasswordUser.getEmail())).thenReturn(false);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPasswordUser)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User registered successfully"));

                // Delete the user after test
                mockMvc.perform(delete("/api/users/delete/" + validPasswordUser.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        }

        @Test
        public void testAddingExistedEmail() throws Exception {
                User existingUser = new User();
                existingUser.setEmail("existing.email1@example.com");
                existingUser.setPassword("existing@Password123");

                // Register the user first
                when(userRepository.existsById(existingUser.getEmail())).thenReturn(false);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingUser)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User registered successfully"));

                // Simulate the user already existing in the repository
                when(userRepository.existsById(existingUser.getEmail())).thenReturn(true);

                // Attempt to register the same user again
                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingUser)))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string("Email already exists!"));

                // Clean up
                mockMvc.perform(delete("/api/users/delete/" + existingUser.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        }

        @Test
        public void testDeleteNonExistingEmail() throws Exception {
                String nonExistingEmail = "non.existing.email@example.com";

                mockMvc.perform(delete("/api/users/delete/" + nonExistingEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(content().string("User not found"));
        }

        @Test
        public void testDeleteExistedEmail() throws Exception {
                User userToDelete = new User();
                userToDelete.setEmail("user.to.delete@example.com");
                userToDelete.setPassword("deletePassword@123");

                // Register the user first
                when(userRepository.existsById(userToDelete.getEmail())).thenReturn(false);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToDelete)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User registered successfully"));

                // Now delete the user
                mockMvc.perform(delete("/api/users/delete/" + userToDelete.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User deleted successfully"));
        }

        @Test
        public void testRequestingResetPassForNonExistingEmail() throws Exception {
                User nonExistingUser = new User();
                nonExistingUser.setEmail("non.existing.email@example.com");

                mockMvc.perform(post("/api/users/resetPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonExistingUser)))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string("Email does not exist"));
        }

        @Test
        public void testRequestingResetPassForExistingEmail() throws Exception {
                User existingUser = new User();
                existingUser.setEmail("existing.emailforresetpass@example.com");
                existingUser.setPassword("existingPassword-123");

                // Register the user first
                when(userRepository.existsById(existingUser.getEmail())).thenReturn(false);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
                when(userRepository.save(existingUser)).thenReturn(existingUser);

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingUser)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User registered successfully"));

                // Now request a password reset for the user
                when(userRepository.existsById(existingUser.getEmail())).thenReturn(true);
                when(userRepository.findById(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

                mockMvc.perform(post("/api/users/resetPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingUser)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Check your email for reset password link"));

                // Clean up
                mockMvc.perform(delete("/api/users/delete/" + existingUser.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        }

        @Test
        public void testResetPasswordWithInvalidEmail() throws Exception {
                Map<String, String> payload;
                payload = Map.of(
                        "email", "nonexistingemail@example.com",
                        "newPassword", "newValidPassword123",
                        "token", "validToken"
                );

                mockMvc.perform(post("/api/users/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string("Invalid email or token"));
        }

        @Test
        public void testResetPasswordWithInvalidToken() throws Exception {
                User existingUser = new User();
                existingUser.setEmail("existedemail@example.com");
                existingUser.setPassword("existing@Password123");
                existingUser.setResetToken("validToken");

                // Register the user first
                when(userRepository.existsById(existingUser.getEmail())).thenReturn(false);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
                when(userRepository.save(existingUser)).thenReturn(existingUser);

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingUser)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User registered successfully"));

                // Simulate the user already existing in the repository
                when(userRepository.existsById(existingUser.getEmail())).thenReturn(true);
                when(userRepository.findById(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

                // Attempt to reset the password with an invalid token
                Map<String, String> payload = Map.of(
                "email", existingUser.getEmail(),
                "newPassword", "newValidPassword123",
                "token", "invalidToken"
                );

                mockMvc.perform(post("/api/users/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string("Invalid email or token"));

                // Clean up
                mockMvc.perform(delete("/api/users/delete/" + existingUser.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        }

        @Test
        public void testValidResetPassword() throws Exception {
                User existingUser = new User();
                existingUser.setEmail("existingemail404@example.com");
                existingUser.setPassword("existing@@Password123");
                existingUser.setResetToken("validToken");

                existingUser.setTokenExpirationTime(LocalDateTime.now().plusMinutes(5));

                // Register the user first
                when(userRepository.existsById(existingUser.getEmail())).thenReturn(false);
                when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
                when(userRepository.save(existingUser)).thenReturn(existingUser);

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingUser)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User registered successfully"));

                // Simulate the user already existing in the repository
                when(userRepository.existsById(existingUser.getEmail())).thenReturn(true);
                when(userRepository.findById(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

                // Attempt to reset the password with a valid token
                Map<String, String> payload = Map.of(
                "email", existingUser.getEmail(),
                "newPassword", "ThePass@1234",
                "token", "validToken"
                );

                mockMvc.perform(post("/api/users/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Password has been reset successfully"));

                // Clean up
                mockMvc.perform(delete("/api/users/delete/" + existingUser.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        }

        @Test
        public void testValidLoginAndLogout() throws Exception {
        User userToLogout = new User();
        userToLogout.setEmail("new.email@example.com");
        userToLogout.setPassword("New-Password123");

        // Register the user first
        when(userRepository.existsById(userToLogout.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(userToLogout)).thenReturn(userToLogout);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToLogout)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

        // Simulate the user already existing in the repository with the encoded password
        when(userRepository.findById(userToLogout.getEmail())).thenReturn(Optional.of(userToLogout));
        when(passwordEncoder.matches("logoutPassword123", "encodedPassword")).thenReturn(true);

        // Log in the user to establish a session
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToLogout)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));

        // Log out the user
        mockMvc.perform(post("/api/users/logout")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Logout successful"));

        // Clean up
        mockMvc.perform(delete("/api/users/delete/" + userToLogout.getEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        }

        @Test
        public void testInvalidLogin() throws Exception {
                User invalidUser = new User();
                invalidUser.setEmail("email0@example.com");
                invalidUser.setPassword("invalidPassword123");

                // Simulate the user not existing in the repository
                when(userRepository.findById(invalidUser.getEmail())).thenReturn(Optional.empty());

                // Attempt to log
                mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string("Invalid email or password"));
        }

        @Test
        public void testValidPostService() throws Exception {
                // Create the PostService object
                PostService postService = new PostService("Service 1", "Description 1");
                postService.setTypeService("Test Service");
                postService.setDescription("This is a test service description");
                postService.setEmailService("test@example.com");
                postService.setPhoneService("123-456-7890");
                postService.setCityService("Test City");
                postService.setProvinceService("Test Province");
                postService.setCountryService("Test Country");
                postService.setPostedBy("user@example.com");

                // Post the service
                mockMvc.perform(post("/api/users/postservices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postService)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Posted Successfully"));
        }

        @Test
        public void testGetAllServices_NoServicesFound() {
                // Mock the postServiceRepository to return an empty list
                when(postServiceRepository.findAll()).thenReturn(Collections.emptyList());

                ResponseEntity<?> response = userController.getAllServices();

                assertEquals("No services found", response.getBody());
                assertEquals(200, response.getStatusCodeValue());
                assertTrue(((String) response.getBody()).contains("No services found"));
        }

        @Test
        public void testPasswordLengthBoundary() throws Exception {
                // Password length at lower boundary (7 characters)
                User shortPasswordUser = new User();
                shortPasswordUser.setEmail("lowerboundary@example.com");
                shortPasswordUser.setPassword("Short7!");

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shortPasswordUser)))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(
                                "Password is invalid. Valid password must contain:<br>" +
                                "At least one uppercase letter<br>" +
                                "At least one lowercase letter<br>" +
                                "At least one digit<br>" +
                                "At least one special character: @, $, !, %, *, ?, & or -<br>" +
                                "Minimum length of 8 characters<br>" +
                                "No spaces"));

                // Password length at boundary (8 characters)
                User validPasswordUser = new User();
                validPasswordUser.setEmail("valid.at8boundary@example.com");
                validPasswordUser.setPassword("Valid8-0");

                mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPasswordUser)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User registered successfully"));

                // Clean up
                mockMvc.perform(delete("/api/users/delete/" + validPasswordUser.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        }


        @Test
        public void testGetAllServices_ServicesFound() {
                // Create a list of services to be returned by the mock
                PostService service1 = new PostService("Service 1", "Description 1");
                PostService service2 = new PostService("Service 2", "Description 2");
                List<PostService> services = List.of(service1, service2);

                // Mock the postServiceRepository to return the list of services
                when(postServiceRepository.findAll()).thenReturn(services);

                ResponseEntity<?> response = userController.getAllServices();

                assertEquals(200, response.getStatusCodeValue());
                assertFalse(((List<PostService>) response.getBody()).isEmpty());
                assertEquals(2, ((List<PostService>) response.getBody()).size());
        }
}
