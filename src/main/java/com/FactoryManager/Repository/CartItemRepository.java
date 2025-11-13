package com.FactoryManager.Repository;

import com.FactoryManager.Entity.CartItem;
import com.FactoryManager.Entity.Product;
import com.FactoryManager.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByDistributorAndProduct(User distributor, Product product);
}
