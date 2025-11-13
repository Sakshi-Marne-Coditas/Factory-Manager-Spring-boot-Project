package com.FactoryManager.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductRequestDto {
    private String name;
    private MultipartFile image; // file from frontend
    private int quantity;
    private Long categoryId;// link to ProductCategory
    private Double price;
    private String description;
}