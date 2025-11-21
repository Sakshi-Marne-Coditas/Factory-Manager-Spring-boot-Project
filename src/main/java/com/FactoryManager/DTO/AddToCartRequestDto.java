package com.FactoryManager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddToCartRequestDto {
    @NotBlank(message = "Id is required")
    @Positive(message = "Id should be greater than zero")
    private Long productId;

    @NotBlank(message = "Quantity is required")
    @Positive(message = "Quantity should be greater than zero")
    private int quantity;
}
