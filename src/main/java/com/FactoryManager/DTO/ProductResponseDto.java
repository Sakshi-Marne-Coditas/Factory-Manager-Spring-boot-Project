package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private int quantity;
    private String categoryName;
    private String description;
    private Double price;
    private String imageUrl;
}
