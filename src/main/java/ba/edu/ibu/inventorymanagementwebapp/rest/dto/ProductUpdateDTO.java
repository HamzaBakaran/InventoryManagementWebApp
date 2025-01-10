package ba.edu.ibu.inventorymanagementwebapp.rest.dto;

public class ProductUpdateDTO {
    private String name;
    private String description;
    private int quantity;
    private int minimalThreshold;
    private Long categoryId;

    // Getters and setters


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
}