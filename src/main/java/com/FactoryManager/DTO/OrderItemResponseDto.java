package com.FactoryManager.DTO;

import lombok.Data;

@Data
public class OrderItemResponseDto {

    private Long productId;
    private String productName;
    private Double price;
    private int quantity;
    private Double subtotal;
    private String image;
}
