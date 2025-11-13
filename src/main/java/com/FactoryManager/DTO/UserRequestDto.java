package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.Role;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRequestDto {

    private String username;


    private String email;

    private Role role;

    private Long fcatory_id;

    private MultipartFile photo;
}
