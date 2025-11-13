package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleCountResponseDto {
    private String role;
    private Long count;

    // ✅ Add matching constructor for JPQL (Role + Long)
    public RoleCountResponseDto(Role role, Long count) {
        this.role = role.name(); // Convert Enum to String
        this.count = count;
    }
}