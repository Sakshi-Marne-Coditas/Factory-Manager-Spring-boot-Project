package com.FactoryManager.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ApproveOrderBatchesRequestDto {
    private Long orderId;
    private List<BatchDto> batches;
}
