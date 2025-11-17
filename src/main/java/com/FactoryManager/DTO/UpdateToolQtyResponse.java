package com.FactoryManager.DTO;

import lombok.Data;

@Data
public class UpdateToolQtyResponse {
    private Long toolId;
    private String toolName;
    private Long factoryId;
    private int updatedCurrentAvailable;
    private int updatedTotalStock;
    private String message;
}