package com.FactoryManager.DTO;

import lombok.Data;

@Data
public class AddToCartRequestDto {
    private Long productId;
    private int quantity;
}
