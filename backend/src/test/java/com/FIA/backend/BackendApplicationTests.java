package com.FIA.backend;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

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
        validUser.setEmail("valid.email11@example.com");
        validUser.setPassword("validPassword123");

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
                .andExpect(content().string("Password must be at least 8 characters long and contain a mix of letters and numbers"));
    }
    
    @Test
    public void testValidPassword() throws Exception {
        User validPasswordUser = new User();
        validPasswordUser.setEmail("valid101@example.com");
        validPasswordUser.setPassword("validPassword123");

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
        existingUser.setPassword("existingPassword123");

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
        userToDelete.setPassword("deletePassword123");

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
        existingUser.setPassword("existingPassword123");

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
    public void testLoginUser() {
        HttpSession session = new MockHttpSession();

        // Mock the userRepository and passwordEncoder
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        ResponseEntity<String> response = userController.loginUser(user, session);

        assertEquals("Login successful", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user.getEmail(), session.getAttribute("userEmail"));
    }

    @Test
    public void testLogoutUser() {
        HttpSession session = new MockHttpSession();
        session.setAttribute("userEmail", user.getEmail());

        ResponseEntity<String> response = userController.logoutUser(session);

        assertEquals("Logout successful", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        //assertEquals(null, session.getAttribute("userEmail")); // Session is invalidated
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

    // @Test
    // public void testInvalidEmailFormat() {
    //     Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    //         user.setEmail("invalid-email");
    //     });
    //     assertEquals("Invalid email format", exception.getMessage());
    // }

    // @Test
    // public void testInvalidPassword() {
    //     Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    //         user.setPassword("123"); // Password too short
    //     });
    //     assertEquals("Invalid password format", exception.getMessage());
    // }

    // @Test
    // public void testGetAllServices_ServicesFound() {
    //     // Create a list of services to be returned by the mock
    //     PostService service1 = new PostService("Service 1", "Description 1");
    //     PostService service2 = new PostService("Service 2", "Description 2");
    //     List<PostService> services = List.of(service1, service2);

    //     // Mock the postServiceRepository to return the list of services
    //     when(postServiceRepository.findAll()).thenReturn(services);

    //     ResponseEntity<?> response = userController.getAllServices();

    //     assertEquals(200, response.getStatusCodeValue());
    //     assertFalse(((List<PostService>) response.getBody()).isEmpty());
    //     assertEquals(2, ((List<PostService>) response.getBody()).size());
    // }
}
