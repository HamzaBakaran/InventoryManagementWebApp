package ba.edu.ibu.inventorymanagementwebapp.rest.controller;

import ba.edu.ibu.inventorymanagementwebapp.core.model.User;
import ba.edu.ibu.inventorymanagementwebapp.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByEmail() {
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail(email);

        when(userService.getUserByEmail(email)).thenReturn(mockUser);

        ResponseEntity<User> response = userController.getUserByEmail(email);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void testCreateUser() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");

        when(userService.saveUser(mockUser)).thenReturn(mockUser);

        ResponseEntity<User> response = userController.createUser(mockUser);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).saveUser(mockUser);
    }

    @Test
    void testUserExists() {
        String email = "test@example.com";

        when(userService.userExists(email)).thenReturn(true);

        ResponseEntity<Boolean> response = userController.userExists(email);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody());
        verify(userService, times(1)).userExists(email);
    }
}
