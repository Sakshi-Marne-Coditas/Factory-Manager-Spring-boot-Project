package com.FactoryManager.Repository;

import com.FactoryManager.Entity.FactoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FactoryProductRepository extends JpaRepository<FactoryProduct, Long> {
    // find FactoryProduct record by productId and factoryId
    Optional<FactoryProduct> findByProductIdAndFactoryId(Long productId, Long factoryId);

    void deleteAllByProductId(Long productId);
}
