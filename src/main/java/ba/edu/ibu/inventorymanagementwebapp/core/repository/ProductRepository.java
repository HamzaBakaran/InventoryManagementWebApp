package ba.edu.ibu.inventorymanagementwebapp.core.repository;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByQuantityLessThan(int threshold);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByUserId(Long userId);

    @Query("SELECT new map(p.id as id, p.name as name, p.description as description, p.quantity as quantity, " +
            "p.minimalThreshold as minimalThreshold, p.categoryId as categoryId, c.name as categoryName) " +
            "FROM Product p JOIN Category c ON p.categoryId = c.id WHERE p.userId = :userId")
    List<Map<String, Object>> findProductsWithCategoryNamesByUserId(@Param("userId") Long userId);



}