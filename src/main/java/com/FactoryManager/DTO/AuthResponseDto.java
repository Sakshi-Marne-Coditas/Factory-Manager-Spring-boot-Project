package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthResponseDto {
    private String message;
    private Long uid;
    private String username;
    private String email;
    private Role role;
    private String token;

}
