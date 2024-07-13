package com.FIA.backend;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@SpringBootTest
class BackendApplicationTests {
	// @Test
	// void contextLoads() {
	// }
    
	// private User user;
    // @BeforeEach
    // public void setUp() {
    //     user = new User();
    // }
    // @Test
    // public void testEmail() {
    //     String email = "test@example.com";
    //     user.setEmail(email);
    //     assertEquals(email, user.getEmail());
    
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
    public void testEmail() {
        String email = "test@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
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
    public void testInvalidEmailFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail("invalid-email");
        });
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    public void testInvalidPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("123"); // Password too short
        });
        assertEquals("Invalid password format", exception.getMessage());
    }

    @Test
    public void testValidEmailFormat() {
        String email = "valid@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testValidPassword() {
        String password = "validPassword123";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
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
