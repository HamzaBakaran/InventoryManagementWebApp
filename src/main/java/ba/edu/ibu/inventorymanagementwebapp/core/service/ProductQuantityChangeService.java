package ba.edu.ibu.inventorymanagementwebapp.core.service;

import ba.edu.ibu.inventorymanagementwebapp.core.repository.ProductQuantityChangeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQuantityChangeService {
    private final ProductQuantityChangeRepository productQuantityChangeRepository;

    public ProductQuantityChangeService(ProductQuantityChangeRepository productQuantityChangeRepository) {
        this.productQuantityChangeRepository = productQuantityChangeRepository;
    }

    public List<Object[]> getTotalItemsSoldByCategory() {
        return productQuantityChangeRepository.getTotalItemsSoldByCategory();
    }
}
