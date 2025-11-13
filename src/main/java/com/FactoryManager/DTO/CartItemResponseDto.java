package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDto {
    private Long productId;
    private String productName;
    private Double productPrice;
    private int quantity;
    private Double itemSubtotal; // productPrice * quantity
}
