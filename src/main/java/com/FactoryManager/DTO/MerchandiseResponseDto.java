package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchandiseResponseDto {
    private Long id;
    private String name;
    private int requiredPts;
    private String image;
}
