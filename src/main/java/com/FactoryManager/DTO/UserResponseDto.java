package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.Role;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;

    private String username;

    private String email;


    private Role role;

}
