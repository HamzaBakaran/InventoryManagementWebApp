package ba.edu.ibu.inventorymanagementwebapp.core.repository;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryRepositoryTest {

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByName() {
        String categoryName = "Electronics";
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName(categoryName);

        when(categoryRepository.findByName(categoryName)).thenReturn(mockCategory);

        Category category = categoryRepository.findByName(categoryName);

        assertNotNull(category);
        assertEquals(categoryName, category.getName());
        assertEquals(1L, category.getId());
        verify(categoryRepository, times(1)).findByName(categoryName);
    }
}