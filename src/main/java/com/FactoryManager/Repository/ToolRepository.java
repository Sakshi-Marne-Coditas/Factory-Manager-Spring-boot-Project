package com.FactoryManager.Repository;

import com.FactoryManager.Entity.Tool;
import com.FactoryManager.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
}
