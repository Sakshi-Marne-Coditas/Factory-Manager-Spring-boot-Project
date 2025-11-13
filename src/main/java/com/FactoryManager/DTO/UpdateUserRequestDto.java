package com.FactoryManager.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

    @Data
    public class UpdateUserRequestDto {
        private Long id;
        private String username;
        private String email;
        private MultipartFile photo;  // optional image file
    }

