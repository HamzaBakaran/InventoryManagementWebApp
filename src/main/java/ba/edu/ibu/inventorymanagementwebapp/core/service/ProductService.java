package ba.edu.ibu.inventorymanagementwebapp.core.service;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Product;
import ba.edu.ibu.inventorymanagementwebapp.core.model.ProductQuantityChange;
import ba.edu.ibu.inventorymanagementwebapp.core.repository.ProductQuantityChangeRepository;
import ba.edu.ibu.inventorymanagementwebapp.core.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductQuantityChangeRepository productQuantityChangeRepository;

    public ProductService(ProductRepository productRepository, ProductQuantityChangeRepository productQuantityChangeRepository) {
        this.productRepository = productRepository;
        this.productQuantityChangeRepository = productQuantityChangeRepository;
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

    // Update quantity and log changes
    public Product updateProductQuantity(Long id, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than 0.");
        }

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int changeAmount = newQuantity - existingProduct.getQuantity();

        if (changeAmount != 0) {
            ProductQuantityChange changeLog = new ProductQuantityChange(existingProduct, changeAmount, LocalDateTime.now());
            productQuantityChangeRepository.save(changeLog);
        }

        existingProduct.setQuantity(newQuantity);

        if (newQuantity <= existingProduct.getMinimalThreshold()) {
            System.out.println("Warning: Product quantity is less than or equal to the minimal threshold.");
        }

        return productRepository.save(existingProduct);
    }

    public List<Map<String, Object>> getProductsWithCategoryNamesByUserId(Long userId) {
        return productRepository.findProductsWithCategoryNamesByUserId(userId);
    }

    public List<Product> getFilteredAndSortedProducts(String name, String description, Integer quantity, Long categoryId, String sortBy, String order) {
        Specification<Product> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (description != null && !description.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
        }
        if (quantity != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("quantity"), quantity));
        }
        if (categoryId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("categoryId"), categoryId));
        }

        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        return productRepository.findAll(spec, sort);
    }

    public List<Product> getFilteredAndSortedProductsByUserId(
            Long userId, String name, String description, Integer quantity, Long categoryId, String sortBy, String order) {

        Specification<Product> spec = Specification.where((root, query, cb) -> cb.equal(root.get("userId"), userId));

        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (description != null && !description.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
        }
        if (quantity != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("quantity"), quantity));
        }
        if (categoryId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("categoryId"), categoryId));
        }

        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        return productRepository.findAll(spec, sort);
    }

    // Generate sales report for a category
    public List<ProductQuantityChange> getSalesReport(Long categoryId, LocalDateTime start, LocalDateTime end) {
        return productQuantityChangeRepository.findByCategoryAndDateRange(categoryId, start, end);
    }
}