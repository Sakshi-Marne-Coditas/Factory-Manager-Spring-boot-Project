package com.FactoryManager.DTO;

import lombok.Data;

@Data
public class OrderBatchItemResponseDto {
    private Long productId;
    private String productName;
    private int quantityDispatched;
    private String image;
}
