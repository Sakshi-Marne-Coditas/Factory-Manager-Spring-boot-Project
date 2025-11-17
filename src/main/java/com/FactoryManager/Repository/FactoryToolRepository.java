package com.FactoryManager.Repository;

import com.FactoryManager.Entity.FactoryTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FactoryToolRepository extends JpaRepository<FactoryTool, Long> {

    Optional<FactoryTool> findByFactoryIdAndToolId(Long factoryId, Long toolId);
}
