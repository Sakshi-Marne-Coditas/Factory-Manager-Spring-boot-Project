package com.FactoryManager.Repository;

import com.FactoryManager.Constatnts.Role;
import com.FactoryManager.DTO.RoleCountResponseDto;
import com.FactoryManager.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByRole(Role role, Pageable pageable);

    Page<User> findByRoleAndFactory_Id(Role role, Long factoryId, Pageable pageable);


    Optional<User> findByUsername(String username);

    Optional<User> findByFactoryIdAndRole(Long factoryId, Role role);


    Optional<User> findByEmail(String email);

    @Query("SELECT new com.FactoryManager.DTO.RoleCountResponseDto(u.role, COUNT(u)) " +
            "FROM User u " +
            "WHERE u.role IN (:roles) " +
            "GROUP BY u.role")
    List<RoleCountResponseDto> getRoleCountsForSpecificRoles(@Param("roles") List<Role> roles);

    List<User> findByRoleAndFactoryIsNull(Role role);

    List<User> findByRoleAndFactory_Id(Role role, Long factoryId);

    Optional<User> deleteByEmail(String email);

    List<User> findByRole(Role role);

}
