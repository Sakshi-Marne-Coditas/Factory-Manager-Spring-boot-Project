package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTotalQuantityResDto {
    private Long productId;
    private String productName;
    private String categoryName;
    private Double productPrice;
    private String productImage;
    private Long totalQuantity;
}
