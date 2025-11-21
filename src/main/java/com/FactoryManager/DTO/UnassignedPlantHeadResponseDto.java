package com.FactoryManager.DTO;

import com.FactoryManager.Constants.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnassignedPlantHeadResponseDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}