package com.FactoryManager.Repository;

import com.FactoryManager.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<Category, Long> {
}
