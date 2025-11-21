package com.FactoryManager.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateFactoryProductQuantityDto {

    @NotNull(message = "Product ID cannot be null")
    @Positive(message = "ID must be greater than 0")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be 0 or greater")
    private Integer quantity;
}
