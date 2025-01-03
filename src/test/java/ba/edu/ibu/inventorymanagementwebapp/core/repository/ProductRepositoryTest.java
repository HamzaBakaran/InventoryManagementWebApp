package ba.edu.ibu.inventorymanagementwebapp.core.repository;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByCategoryId() {
        Long categoryId = 1L;
        List<Product> mockProducts = List.of(
                new Product("Product1", "Description1", 10, 5, categoryId, 1L),
                new Product("Product2", "Description2", 20, 10, categoryId, 1L)
        );

        when(productRepository.findByCategoryId(categoryId)).thenReturn(mockProducts);

        List<Product> products = productRepository.findByCategoryId(categoryId);

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(categoryId, products.get(0).getCategoryId());
        verify(productRepository, times(1)).findByCategoryId(categoryId);
    }

    @Test
    void testFindByQuantityLessThan() {
        int threshold = 10;
        List<Product> mockProducts = List.of(
                new Product("Product1", "Description1", 5, 2, 1L, 1L),
                new Product("Product2", "Description2", 8, 3, 2L, 1L)
        );

        when(productRepository.findByQuantityLessThan(threshold)).thenReturn(mockProducts);

        List<Product> products = productRepository.findByQuantityLessThan(threshold);

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByQuantityLessThan(threshold);
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        String name = "prod";
        List<Product> mockProducts = List.of(
                new Product("Product1", "Description1", 10, 5, 1L, 1L),
                new Product("Product2", "Description2", 20, 10, 2L, 1L)
        );

        when(productRepository.findByNameContainingIgnoreCase(name)).thenReturn(mockProducts);

        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByNameContainingIgnoreCase(name);
    }

    @Test
    void testFindByUserId() {
        Long userId = 1L;
        List<Product> mockProducts = List.of(
                new Product("Product1", "Description1", 10, 5, 1L, userId),
                new Product("Product2", "Description2", 20, 10, 2L, userId)
        );

        when(productRepository.findByUserId(userId)).thenReturn(mockProducts);

        List<Product> products = productRepository.findByUserId(userId);

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testFindProductsWithCategoryNamesByUserId() {
        Long userId = 1L;
        List<Map<String, Object>> mockResults = List.of(
                Map.of("id", 1L, "name", "Product1", "description", "Description1", "quantity", 10,
                        "minimalThreshold", 5, "categoryId", 1L, "categoryName", "Category1"),
                Map.of("id", 2L, "name", "Product2", "description", "Description2", "quantity", 20,
                        "minimalThreshold", 10, "categoryId", 2L, "categoryName", "Category2")
        );

        when(productRepository.findProductsWithCategoryNamesByUserId(userId)).thenReturn(mockResults);

        List<Map<String, Object>> results = productRepository.findProductsWithCategoryNamesByUserId(userId);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Product1", results.get(0).get("name"));
        verify(productRepository, times(1)).findProductsWithCategoryNamesByUserId(userId);
    }
}
