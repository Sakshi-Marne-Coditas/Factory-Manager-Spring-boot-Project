package com.FactoryManager.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderBatchDto {
    private Long id;
    private int quantityDispatched;
    private LocalDateTime dispatchedAt;
    private LocalDateTime expectedDeliveryDate;
    private String status;
}
