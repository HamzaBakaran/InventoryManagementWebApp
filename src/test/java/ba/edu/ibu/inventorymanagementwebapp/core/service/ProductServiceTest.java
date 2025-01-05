package ba.edu.ibu.inventorymanagementwebapp.core.service;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Product;
import ba.edu.ibu.inventorymanagementwebapp.core.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product mockProduct = new Product("Product1", "Description1", 10, 5, 1L, 1L);
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);

        Product createdProduct = productService.createProduct(mockProduct);

        assertNotNull(createdProduct);
        assertEquals("Product1", createdProduct.getName());
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        Product existingProduct = new Product("Product1", "Description1", 10, 5, 1L, 1L);
        existingProduct.setId(productId);

        Product updatedProduct = new Product("UpdatedProduct", "UpdatedDescription", 15, 8, 2L, 1L);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product result = productService.updateProduct(productId, updatedProduct);

        assertNotNull(result);
        assertEquals("UpdatedProduct", result.getName());
        assertEquals(15, result.getQuantity());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product mockProduct = new Product("Product1", "Description1", 10, 5, 1L, 1L);
        mockProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Product product = productService.getProductById(productId);

        assertNotNull(product);
        assertEquals(productId, product.getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductsByUserId() {
        Long userId = 1L;
        List<Product> mockProducts = List.of(
                new Product("Product1", "Description1", 10, 5, 1L, userId),
                new Product("Product2", "Description2", 20, 10, 2L, userId)
        );

        when(productRepository.findByUserId(userId)).thenReturn(mockProducts);

        List<Product> products = productService.getProductsByUserId(userId);

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetProductsByCategoryId() {
        Long categoryId = 1L;
        List<Product> mockProducts = List.of(
                new Product("Product1", "Description1", 10, 5, categoryId, 1L),
                new Product("Product2", "Description2", 20, 10, categoryId, 1L)
        );

        when(productRepository.findByCategoryId(categoryId)).thenReturn(mockProducts);

        List<Product> products = productService.getProductsByCategoryId(categoryId);

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findByCategoryId(categoryId);
    }

    @Test
    void testGetAllProducts() {
        List<Product> mockProducts = List.of(
                new Product("Product1", "Description1", 10, 5, 1L, 1L),
                new Product("Product2", "Description2", 20, 10, 2L, 1L)
        );

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProductQuantity() {
        Long productId = 1L;
        int newQuantity = 15;
        Product existingProduct = new Product("Product1", "Description1", 10, 5, 1L, 1L);
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product updatedProduct = productService.updateProductQuantity(productId, newQuantity);

        assertNotNull(updatedProduct);
        assertEquals(newQuantity, updatedProduct.getQuantity());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testGetProductsWithCategoryNamesByUserId() {
        Long userId = 1L;
        List<Map<String, Object>> mockResults = List.of(
                Map.of("id", 1L, "name", "Product1", "description", "Description1", "quantity", 10,
                        "minimalThreshold", 5, "categoryId", 1L, "categoryName", "Category1"),
                Map.of("id", 2L, "name", "Product2", "description", "Description2", "quantity", 20,
                        "minimalThreshold", 10, "categoryId", 2L, "categoryName", "Category2")
        );

        when(productRepository.findProductsWithCategoryNamesByUserId(userId)).thenReturn(mockResults);

        List<Map<String, Object>> results = productService.getProductsWithCategoryNamesByUserId(userId);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Product1", results.get(0).get("name"));
        verify(productRepository, times(1)).findProductsWithCategoryNamesByUserId(userId);
    }
}
