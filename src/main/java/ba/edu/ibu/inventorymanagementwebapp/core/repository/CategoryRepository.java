package ba.edu.ibu.inventorymanagementwebapp.core.repository;

import ba.edu.ibu.inventorymanagementwebapp.core.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}