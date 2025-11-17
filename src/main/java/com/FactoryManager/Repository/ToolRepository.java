package com.FactoryManager.Repository;

import com.FactoryManager.Entity.Tool;
import com.FactoryManager.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    Optional<Tool> findByToolName(String toolName);

    @Query("""
    SELECT t FROM Tool t
    WHERE (:search IS NULL OR :search = '' 
           OR LOWER(t.toolName) LIKE LOWER(CONCAT('%', :search, '%')))
      AND (:categoryId IS NULL OR t.category.id = :categoryId)
    ORDER BY t.toolName
""")
    Page<Tool> searchTools(
            @Param("search") String search,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );
}
