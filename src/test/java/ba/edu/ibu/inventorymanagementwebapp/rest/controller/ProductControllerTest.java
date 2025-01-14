package ba.edu.ibu.inventorymanagementwebapp.rest.controller;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Product;
import ba.edu.ibu.inventorymanagementwebapp.core.service.EmailService;
import ba.edu.ibu.inventorymanagementwebapp.core.service.ProductService;
import ba.edu.ibu.inventorymanagementwebapp.rest.dto.ProductUpdateDTO;
import ba.edu.ibu.inventorymanagementwebapp.rest.responses.WarningResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
    void testGetFilteredAndSortedProducts() {
        List<Product> mockProducts = List.of(
                new Product("ProductA", "DescriptionA", 5, 2, 1L, 1L),
                new Product("ProductB", "DescriptionB", 10, 3, 2L, 1L)
        );

        when(productService.getFilteredAndSortedProducts("Product", null, null, null, "name", "asc"))
                .thenReturn(mockProducts);

        ResponseEntity<List<Product>> response = productController.getFilteredAndSortedProducts(
                "Product", null, null, null, "name", "asc"
        );

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProducts, response.getBody());
        verify(productService, times(1)).getFilteredAndSortedProducts("Product", null, null, null, "name", "asc");
    }

    @Test
    void testGetFilteredAndSortedProductsByUserId() {
        Long userId = 1L;
        List<Product> mockProducts = List.of(
                new Product("ProductA", "DescriptionA", 5, 2, 1L, userId),
                new Product("ProductB", "DescriptionB", 10, 3, 2L, userId)
        );

        when(productService.getFilteredAndSortedProductsByUserId(userId, "Product", null, null, null, "name", "asc"))
                .thenReturn(mockProducts);

        ResponseEntity<List<Product>> response = productController.getFilteredAndSortedProductsByUserId(
                userId, "Product", null, null, null, "name", "asc"
        );

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProducts, response.getBody());
        verify(productService, times(1))
                .getFilteredAndSortedProductsByUserId(userId, "Product", null, null, null, "name", "asc");
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
        Product existingProduct = new Product("ExistingProduct", "ExistingDescription", 10, 5, 1L, 1L);
        Product updatedProduct = new Product("UpdatedProduct", "UpdatedDescription", 15, 8, 2L, 1L);
        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
        productUpdateDTO.setName("UpdatedProduct");
        productUpdateDTO.setDescription("UpdatedDescription");
        productUpdateDTO.setQuantity(15);
        productUpdateDTO.setMinimalThreshold(8);
        productUpdateDTO.setCategoryId(2L);

        when(productService.getProductById(productId)).thenReturn(existingProduct);
        when(productService.updateProduct(productId, existingProduct)).thenReturn(updatedProduct);

        ResponseEntity<Object> response = productController.updateProduct(productId, productUpdateDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedProduct, response.getBody());

        verify(productService, times(1)).getProductById(productId);
        verify(productService, times(1)).updateProduct(productId, existingProduct);
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
    void testUpdateProductQuantity() {
        Long productId = 1L;
        int quantity = 3;
        Product mockProduct = new Product("Product1", "Description1", 10, 5, 1L, 1L);
        mockProduct.setQuantity(quantity);

        when(productService.updateProductQuantity(productId, quantity)).thenReturn(mockProduct);

        ResponseEntity<Object> response = productController.updateProductQuantity(productId, quantity);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).updateProductQuantity(productId, quantity);
    }
}
