package com.FactoryManager.Repository;

import com.FactoryManager.Entity.OrderBatchItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBatchItemRepository extends JpaRepository<OrderBatchItem, Long> {}
