package com.FactoryManager.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderBatchResponseDto {
    private Long batchId;
    private LocalDateTime dispatchedAt;
    private LocalDateTime expectedDeliveryDate;
    private String status;
    private List<OrderBatchItemResponseDto> items;
}

