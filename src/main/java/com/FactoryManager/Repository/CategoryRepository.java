package com.FactoryManager.Repository;

import com.FactoryManager.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryNameIgnoreCase(String categoryName);
}
