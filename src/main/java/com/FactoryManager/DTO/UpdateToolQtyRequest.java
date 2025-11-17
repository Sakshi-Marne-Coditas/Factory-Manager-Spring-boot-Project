package com.FactoryManager.DTO;

import lombok.Data;

@Data
public class UpdateToolQtyRequest {
    private Long toolId;
    private int increaseBy; // qty to add
}
