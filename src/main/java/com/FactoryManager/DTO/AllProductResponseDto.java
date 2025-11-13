package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllProductResponseDto {
    private Long id;
    private String name;
    private String categoryName;
    private String description;
    private Double price;
    private String imageUrl;
}
