package com.FactoryManager.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApproveOrderBatchesResponseDto {
    private Long orderId;
    private List<OrderBatchResponseDto> batches;
    private LocalDateTime finalEstimatedDelivery;   // last batch delivery date
}
