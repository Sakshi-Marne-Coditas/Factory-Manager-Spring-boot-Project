package com.FactoryManager.DTO;

import lombok.Data;

@Data
public class ToolResponseDto {

    private Long id;
    private String toolName;
    private String toolDescription;
    private String toolImage;

    private String categoryName;
    private String storageCode;

    private String useCase;
    private String toolType;

    private int thresholdQty;
    private int totalStock;

    private Integer currentAvailable;  //  new field
}

