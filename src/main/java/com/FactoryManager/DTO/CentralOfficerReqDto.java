package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.Role;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CentralOfficerReqDto {
    private String username;

    private String email;

    private MultipartFile photo;
}
