package com.FactoryManager.Repository;

import com.FactoryManager.Constatnts.RequestStatus;
import com.FactoryManager.Entity.Order;
import com.FactoryManager.Entity.OrderBatch;
import com.FactoryManager.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByStatus(RequestStatus status, Pageable pageable);

    Page<Order> findByDistributor(User distributor, Pageable pageable);

}
