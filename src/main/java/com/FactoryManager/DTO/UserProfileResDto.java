package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.Role;
import lombok.Data;

@Data
public class UserProfileResDto {
    private Long id;

    private String username;

    private String email;

    private String role;

    private String factoryName;

    private String photo;
}
