package com.FactoryManager.Repository;

import com.FactoryManager.Entity.Bay;
import com.FactoryManager.Entity.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BayRepository extends JpaRepository<Bay, Long> {
}
