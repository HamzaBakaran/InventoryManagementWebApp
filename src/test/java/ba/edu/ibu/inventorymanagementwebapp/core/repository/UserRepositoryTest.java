package ba.edu.ibu.inventorymanagementwebapp.core.repository;

import ba.edu.ibu.inventorymanagementwebapp.core.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        User user = userRepository.findByEmail(email);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(1L, user.getId());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testExistsByEmail() {
        String email = "test@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean exists = userRepository.existsByEmail(email);

        assertTrue(exists);
        verify(userRepository, times(1)).existsByEmail(email);
    }
}