package com.FactoryManager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CentralOfficeRequestDto {
    @NotBlank(message = "Id cannot be blank")
    @NotNull(message = "Id can't be null")
    @Positive(message = "id must be greater than zero")
    private Long productId;

    @NotBlank(message = "Id cannot be blank")
    @NotNull(message = "Id can't be null")
    @Positive(message = "Qty must be greater than zero")
    private int requiredQuantity;
}
