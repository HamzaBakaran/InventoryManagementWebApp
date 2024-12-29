package ba.edu.ibu.inventorymanagementwebapp.rest.controller;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Product;
import ba.edu.ibu.inventorymanagementwebapp.core.service.EmailService;
import ba.edu.ibu.inventorymanagementwebapp.core.service.ProductService;
import ba.edu.ibu.inventorymanagementwebapp.rest.responses.WarningResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final EmailService emailService;

    public ProductController(ProductService productService, EmailService emailService) {
        this.productService = productService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable Long userId) {
        List<Product> products = productService.getProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<Object> updateProductQuantity(@PathVariable Long id, @RequestParam int quantity) {
        try {
            Product updatedProduct = productService.updateProductQuantity(id, quantity);

            if (quantity <= updatedProduct.getMinimalThreshold()) {
                // Send email notification
                String subject = "Low Stock Alert: " + updatedProduct.getName();
                String body = String.format(
                        "Dear user,\n\nThe product '%s' has reached or fallen below the minimal threshold of %d units.\n" +
                                "Current quantity: %d.\n\nPlease take appropriate action.",
                        updatedProduct.getName(),
                        updatedProduct.getMinimalThreshold(),
                        quantity
                );


                String userEmail = "kadet.bakaran.hamza@gmail.com"; // email slanje radi samo za verifikovanog usera na mailgun-u
                emailService.sendEmail(userEmail, subject, body);

                return ResponseEntity.ok(new WarningResponse(
                        "Product updated successfully. Warning: Quantity is less than or equal to the minimal threshold. An email notification has been sent.",
                        updatedProduct
                ));
            }

            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
