package com.FactoryManager.DTO;

import lombok.Data;

@Data
public class AddToolResponseDto {

    private Long id;

    private String toolName;
    private String toolDescription;
    private String toolImage;

    private String categoryName;      // From ToolCategory

    private int thresholdQty;

    private String useCase;           // Optional: for readability on FE
    private String toolType;
}
