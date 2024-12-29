package ba.edu.ibu.inventorymanagementwebapp.core.repository;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByQuantityLessThan(int threshold);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByUserId(Long userId);


}