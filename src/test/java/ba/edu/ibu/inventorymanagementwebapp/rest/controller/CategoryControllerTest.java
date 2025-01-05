package ba.edu.ibu.inventorymanagementwebapp.rest.controller;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Category;
import ba.edu.ibu.inventorymanagementwebapp.core.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

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

        when(categoryService.getAllCategories()).thenReturn(mockCategories);

        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCategories, response.getBody());
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testGetCategoryById() {
        Long categoryId = 1L;
        Category mockCategory = new Category("Category1");
        mockCategory.setId(categoryId);

        when(categoryService.getCategoryById(categoryId)).thenReturn(mockCategory);

        ResponseEntity<Category> response = categoryController.getCategoryById(categoryId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCategory, response.getBody());
        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    void testCreateCategory() {
        Category mockCategory = new Category("Category1");

        when(categoryService.saveCategory(mockCategory)).thenReturn(mockCategory);

        ResponseEntity<Category> response = categoryController.createCategory(mockCategory);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockCategory, response.getBody());
        verify(categoryService, times(1)).saveCategory(mockCategory);
    }

    @Test
    void testUpdateCategory() {
        Long categoryId = 1L;
        Category updatedCategory = new Category("UpdatedCategory");
        updatedCategory.setId(categoryId);

        when(categoryService.saveCategory(updatedCategory)).thenReturn(updatedCategory);

        ResponseEntity<Category> response = categoryController.updateCategory(categoryId, updatedCategory);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedCategory, response.getBody());
        verify(categoryService, times(1)).saveCategory(updatedCategory);
    }

    @Test
    void testDeleteCategory() {
        Long categoryId = 1L;

        doNothing().when(categoryService).deleteCategory(categoryId);

        ResponseEntity<Void> response = categoryController.deleteCategory(categoryId);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(categoryService, times(1)).deleteCategory(categoryId);
    }
}