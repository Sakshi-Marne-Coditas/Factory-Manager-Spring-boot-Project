package com.FactoryManager.DTO;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BatchProductDto {
    @Positive(message = "Id should be greater than zero")
    private Long productId;

    @Positive(message = "Id should be greater than zero")
    private int quantityDispatched;
}

