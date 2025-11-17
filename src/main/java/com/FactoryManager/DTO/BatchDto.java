package com.FactoryManager.DTO;

import lombok.Data;

import java.util.List;

@Data
public class BatchDto {
    private List<BatchProductDto> products;
}