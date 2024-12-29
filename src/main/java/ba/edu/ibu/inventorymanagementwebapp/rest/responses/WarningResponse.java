package ba.edu.ibu.inventorymanagementwebapp.rest.responses;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Product;

public class WarningResponse {
    private String message;
    private Product product;

    public WarningResponse(String message, Product product) {
        this.message = message;
        this.product = product;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
