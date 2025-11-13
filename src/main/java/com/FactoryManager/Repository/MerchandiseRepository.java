package com.FactoryManager.Repository;

import com.FactoryManager.Entity.Merchandise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {
    Page<Merchandise> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
