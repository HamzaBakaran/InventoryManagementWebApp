package ba.edu.ibu.inventorymanagementwebapp.core.service;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Product;
import ba.edu.ibu.inventorymanagementwebapp.core.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setMinimalThreshold(updatedProduct.getMinimalThreshold());
        existingProduct.setCategoryId(updatedProduct.getCategoryId());
        existingProduct.setUserId(updatedProduct.getUserId());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Method to update quantity with validation and warnings
    public Product updateProductQuantity(Long id, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than 0.");
        }

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setQuantity(quantity);

        // Check if the quantity is less than or equal to the minimal threshold
        if (quantity <= existingProduct.getMinimalThreshold()) {
            System.out.println("Warning: Product quantity is less than or equal to the minimal threshold.");
        }

        return productRepository.save(existingProduct);
    }
}
