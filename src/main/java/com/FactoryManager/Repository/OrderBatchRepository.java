package com.FactoryManager.Repository;

import com.FactoryManager.Entity.OrderBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBatchRepository extends JpaRepository<OrderBatch, Long> {
}
