package ba.edu.ibu.inventorymanagementwebapp.core.repository;

import ba.edu.ibu.inventorymanagementwebapp.core.model.ProductQuantityChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductQuantityChangeRepository extends JpaRepository<ProductQuantityChange, Long> {

    @Query("SELECT p FROM ProductQuantityChange p WHERE p.product.categoryId = :categoryId AND p.timestamp BETWEEN :start AND :end")
    List<ProductQuantityChange> findByCategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
/*
    @Query("SELECT c.id AS categoryId, c.name AS categoryName, SUM(ABS(pqc.changeAmount)) AS totalSold " +
            "FROM ProductQuantityChange pqc " +
            "JOIN pqc.product p " +
            "JOIN Category c ON p.categoryId = c.id " +
            "WHERE pqc.changeAmount < 0 " + // Only consider reductions
            "GROUP BY c.id, c.name")
    List<Object[]> getTotalItemsSoldByCategory();

 */

    @Query("SELECT c.id AS categoryId, c.name AS categoryName, SUM(ABS(pqc.changeAmount)) AS totalSold " +
            "FROM ProductQuantityChange pqc " +
            "JOIN pqc.product p " +
            "JOIN Category c ON p.categoryId = c.id " +
            "WHERE pqc.changeAmount < 0 AND " +
            "DATE(pqc.timestamp) = CURRENT_DATE " + // Filter for today's date
            "GROUP BY c.id, c.name")
    List<Object[]> getTotalItemsSoldByCategory();





}
