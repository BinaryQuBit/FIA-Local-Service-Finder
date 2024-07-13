package com.FIA.backend;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class BackendApplicationTests {
	// @Test
	// void contextLoads() {
	// }
	private User user;
    @BeforeEach
    public void setUp() {
        user = new User();
    }
    @Test
    public void testEmail() {
        String email = "test@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

}
