package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutPreviewItemDto {
    private Long productId;
    private String productName;
    private Double productPrice;
    private int quantity;
    private Double itemTotal;
}
