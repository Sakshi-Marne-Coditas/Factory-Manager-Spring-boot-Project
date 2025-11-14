package com.FactoryManager.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProfileReqDto {
    private String username;
    private String email;
    private MultipartFile photo;
    private Long factoryId;   // optional
}
