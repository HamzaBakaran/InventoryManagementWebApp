package ba.edu.ibu.inventorymanagementwebapp.core.model;

import jakarta.persistence.*;
import java.util.Objects;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int minimalThreshold;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    // Constructor with Dependency Injection
    public Product(String name, String description, int quantity, int minimalThreshold, Long categoryId, Long userId) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.minimalThreshold = minimalThreshold;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public Product() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinimalThreshold() {
        return minimalThreshold;
    }

    public void setMinimalThreshold(int minimalThreshold) {
        this.minimalThreshold = minimalThreshold;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
