package ba.edu.ibu.inventorymanagementwebapp.rest.controller;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Product;
import ba.edu.ibu.inventorymanagementwebapp.core.service.EmailService;
import ba.edu.ibu.inventorymanagementwebapp.core.service.ProductService;
import ba.edu.ibu.inventorymanagementwebapp.rest.responses.WarningResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product mockProduct = new Product("Product1", "Description1", 10, 5, 1L, 1L);

        when(productService.createProduct(mockProduct)).thenReturn(mockProduct);

        ResponseEntity<Product> response = productController.createProduct(mockProduct);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProduct, response.getBody());
        verify(productService, times(1)).createProduct(mockProduct);
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        Product updatedProduct = new Product("UpdatedProduct", "UpdatedDescription", 15, 8, 2L, 1L);

        when(productService.updateProduct(productId, updatedProduct)).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(productId, updatedProduct);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedProduct, response.getBody());
        verify(productService, times(1)).updateProduct(productId, updatedProduct);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        doNothing().when(productService).deleteProduct(productId);

        ResponseEntity<Void> response = productController.deleteProduct(productId);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product mockProduct = new Product("Product1", "Description1", 10, 5, 1L, 1L);

        when(productService.getProductById(productId)).thenReturn(mockProduct);

        ResponseEntity<Product> response = productController.getProductById(productId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProduct, response.getBody());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void testGetAllProducts() {
        List<Product> mockProducts = List.of(
                new Product("Product1", "Description1", 10, 5, 1L, 1L),
                new Product("Product2", "Description2", 20, 10, 2L, 1L)
        );

        when(productService.getAllProducts()).thenReturn(mockProducts);

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProducts, response.getBody());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductsByUserId() {
        Long userId = 1L;
        List<Map<String, Object>> mockProducts = List.of(
                Map.of("id", 1L, "name", "Product1", "quantity", 10, "minimalThreshold", 5),
                Map.of("id", 2L, "name", "Product2", "quantity", 20, "minimalThreshold", 10)
        );

        when(productService.getProductsWithCategoryNamesByUserId(userId)).thenReturn(mockProducts);

        ResponseEntity<List<Map<String, Object>>> response = productController.getProductsByUserId(userId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProducts, response.getBody());
        verify(productService, times(1)).getProductsWithCategoryNamesByUserId(userId);
    }

    @Test
    void testGetProductsByCategoryId() {
        Long categoryId = 1L;
        List<Product> mockProducts = List.of(
                new Product("Product1", "Description1", 10, 5, categoryId, 1L),
                new Product("Product2", "Description2", 20, 10, categoryId, 1L)
        );

        when(productService.getProductsByCategoryId(categoryId)).thenReturn(mockProducts);

        ResponseEntity<List<Product>> response = productController.getProductsByCategoryId(categoryId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProducts, response.getBody());
        verify(productService, times(1)).getProductsByCategoryId(categoryId);
    }

    @Test
    void testUpdateProductQuantity() {
        Long productId = 1L;
        int quantity = 3; // Less than or equal to minimal threshold
        Product mockProduct = new Product("Product1", "Description1", 10, 5, 1L, 1L);
        mockProduct.setQuantity(quantity);

        when(productService.updateProductQuantity(productId, quantity)).thenReturn(mockProduct);

        String subject = "Low Stock Alert: Product1";
        String body = String.format(
                "Dear user,\n\nThe product '%s' has reached or fallen below the minimal threshold of %d units.\n" +
                        "Current quantity: %d.\n\nPlease take appropriate action.",
                mockProduct.getName(),
                mockProduct.getMinimalThreshold(),
                quantity
        );

        doNothing().when(emailService).sendEmail(anyString(), eq(subject), eq(body));

        ResponseEntity<Object> response = productController.updateProductQuantity(productId, quantity);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        if (response.getBody() instanceof WarningResponse warningResponse) {
            assertEquals("Product updated successfully. Warning: Quantity is less than or equal to the minimal threshold. An email notification has been sent.", warningResponse.getMessage());
            assertEquals(mockProduct, warningResponse.getProduct());
        } else if (response.getBody() instanceof Product product) {
            assertEquals(mockProduct, product);
        }

        verify(productService, times(1)).updateProductQuantity(productId, quantity);
        verify(emailService, times(1)).sendEmail(anyString(), eq(subject), eq(body));
    }
}
