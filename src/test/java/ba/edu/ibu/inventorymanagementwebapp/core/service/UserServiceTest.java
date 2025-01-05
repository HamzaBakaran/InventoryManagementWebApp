package ba.edu.ibu.inventorymanagementwebapp.core.service;

import ba.edu.ibu.inventorymanagementwebapp.core.model.User;
import ba.edu.ibu.inventorymanagementwebapp.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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

        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        User user = userService.getUserByEmail(email);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(1L, user.getId());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testSaveUser() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");

        when(userRepository.save(mockUser)).thenReturn(mockUser);

        User savedUser = userService.saveUser(mockUser);

        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
        assertEquals("test@example.com", savedUser.getEmail());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testUserExists() {
        String email = "test@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean exists = userService.userExists(email);

        assertTrue(exists);
        verify(userRepository, times(1)).existsByEmail(email);
    }
}
