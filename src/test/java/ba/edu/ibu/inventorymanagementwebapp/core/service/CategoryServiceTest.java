package ba.edu.ibu.inventorymanagementwebapp.core.service;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Category;
import ba.edu.ibu.inventorymanagementwebapp.core.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        List<Category> mockCategories = List.of(
                new Category("Category1"),
                new Category("Category2")
        );

        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<Category> categories = categoryService.getAllCategories();

        assertNotNull(categories);
        assertEquals(2, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById() {
        Long categoryId = 1L;
        Category mockCategory = new Category("Category1");
        mockCategory.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));

        Category category = categoryService.getCategoryById(categoryId);

        assertNotNull(category);
        assertEquals(categoryId, category.getId());
        assertEquals("Category1", category.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testSaveCategory() {
        Category mockCategory = new Category("Category1");

        when(categoryRepository.save(mockCategory)).thenReturn(mockCategory);

        Category savedCategory = categoryService.saveCategory(mockCategory);

        assertNotNull(savedCategory);
        assertEquals("Category1", savedCategory.getName());
        verify(categoryRepository, times(1)).save(mockCategory);
    }

    @Test
    void testDeleteCategory() {
        Long categoryId = 1L;

        doNothing().when(categoryRepository).deleteById(categoryId);

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testGetCategoryByName() {
        String categoryName = "Category1";
        Category mockCategory = new Category(categoryName);

        when(categoryRepository.findByName(categoryName)).thenReturn(mockCategory);

        Category category = categoryService.getCategoryByName(categoryName);

        assertNotNull(category);
        assertEquals(categoryName, category.getName());
        verify(categoryRepository, times(1)).findByName(categoryName);
    }
}
