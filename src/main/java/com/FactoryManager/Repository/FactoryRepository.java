package com.FactoryManager.Repository;

import com.FactoryManager.DTO.LocationFactoryCountResponseDto;
import com.FactoryManager.Entity.Factory;
import com.FactoryManager.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {
    Optional<Factory> findByName(String name);

    // ✅ JPQL to count factories grouped by location
    @Query("SELECT new com.FactoryManager.DTO.LocationFactoryCountResponseDto(f.location, COUNT(f)) " +
            "FROM Factory f GROUP BY f.location ORDER BY f.location ASC")
    List<LocationFactoryCountResponseDto> getLocationWiseFactoryCount();
}
