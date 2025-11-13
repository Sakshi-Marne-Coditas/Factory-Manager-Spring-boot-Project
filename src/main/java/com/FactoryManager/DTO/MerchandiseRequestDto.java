package com.FactoryManager.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MerchandiseRequestDto {
    private String name;
    private int requiredPts;
    private MultipartFile image;
}
